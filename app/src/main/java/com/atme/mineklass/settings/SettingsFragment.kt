package com.atme.mineklass.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.atme.mineklass.databinding.FragmentSettingsBinding
import com.atme.mineklass.utils.JsonUtils.getClassFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

// TODO add progress dialogs.
// TODO show notifications when class is about to start.
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSettingsBinding.inflate(inflater, container, false)


        viewModel.refreshClassData.observe(viewLifecycleOwner, {
            if (it == true) {

                Toast.makeText(context, "Database updated!", Toast.LENGTH_SHORT).show()
            } else if (it == false) {
                Toast.makeText(
                    context,
                    "Something went wrong. Please check your internet!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewModel.doneRefresh()
        })

        viewModel.insertFromJson.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.doneInsert()
                Toast.makeText(context, "Added class from file.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshClassData()
        }

        binding.changeButton.setOnClickListener {
            Toast.makeText(context, "To be implemented!!", Toast.LENGTH_SHORT).show()
        }

        binding.readFromJson.setOnClickListener {
            getFile()
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

    private fun getFromJson(uri: Uri) {

        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            if (inputStream != null) {
                try {
                    val bufferedReader = inputStream.bufferedReader()
                    // The program will throw an exception if the json file is formatted.
                    val jsonString = bufferedReader.use { it.readLine() }

                    val classData =
                        getClassFromJson(jsonString)

                    if (classData != null) {
                        viewModel.insertFromJson(classData)
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

}

