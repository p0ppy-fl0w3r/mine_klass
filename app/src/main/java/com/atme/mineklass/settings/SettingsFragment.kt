package com.atme.mineklass.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.atme.mineklass.Constants
import com.atme.mineklass.databinding.FragmentSettingsBinding
import com.atme.mineklass.utils.JsonUtils.getClassFromJson
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.apache.commons.io.IOUtils
import timber.log.Timber

// TODO add progress dialogs.
// TODO request permissions
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by lazy { ViewModelProvider(this).get(SettingsViewModel::class.java) }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data.let { mUri ->
                    if (mUri != null) {
                        getFromJson(mUri)
                    }
                }
            }
        }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)


        viewModel.refreshClassData.observe(viewLifecycleOwner) {
            Timber.e("The state has changed to $it")
            if (it == true) {

                Toast.makeText(context, "Database updated!", Toast.LENGTH_SHORT).show()
                // Calling doneRefresh from outside the if block will cause the observer to go on an infinite loop
                viewModel.doneRefresh()
            } else if (it == false) {
                Toast.makeText(
                    context,
                    "Something went wrong. Please check your internet!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.doneRefresh()
            }

        }

        viewModel.insertFromJson.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.doneInsert()
                binding.readProgress.visibility = View.GONE
                Toast.makeText(context, "Added class from file.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.darkMode.observe(viewLifecycleOwner) {
            binding.darkModeSwitch.isChecked = it == true
        }

        binding.readFromJson.setOnClickListener {
            getFile()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteAll()
        }

        binding.darkModeSwitch.setOnClickListener {
            when (binding.darkModeSwitch.isChecked) {
                true -> {
                    Snackbar.make(
                        binding.root,
                        "Dark mode will not work properly in older devices!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            viewModel.changeDarkStatus(binding.darkModeSwitch.isChecked)
        }

        return binding.root
    }

    private fun getFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        }
        resultLauncher.launch(intent)
    }

    private fun requestReadPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getFile()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.READ_REQUEST_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getFile()
            } else {
                Toast.makeText(requireContext(), "File read permission denied!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    // TODO add loading animation
    private fun getFromJson(uri: Uri) {
        binding.readProgress.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            try {
                inputStream.use {
                    val jsonString = IOUtils.toString(it)
                    val classData = getClassFromJson(jsonString)

                    if (classData != null) {
                        viewModel.insertFromJson(classData)
                    } else {
                        viewModel.doneInsert()
                    }

                }

            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong reading the file!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.doneInsert()

                Timber.e("File reading failed: ${e.message}")
            }

        }
    }

}

