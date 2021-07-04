package com.atme.mineklass.settings

import android.app.Activity
import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.databinding.FragmentSettingsBinding
import com.atme.mineklass.utils.JsonUtils
import com.atme.mineklass.utils.JsonUtils.getClassFromJson
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.InputStream
import java.lang.Exception


class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by lazy { ViewModelProvider(this).get(SettingsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSettingsBinding.inflate(inflater, container, false)



        viewModel.refreshClassData.observe(viewLifecycleOwner, {
            if (it == true) {
                viewModel.refreshClassData()
                viewModel.doneRefresh()
                Toast.makeText(context, "Database updated!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.insertFromJson.observe(viewLifecycleOwner){
            if(it == true){
                Toast.makeText(context, "Added class from file.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refreshButton.setOnClickListener {
            viewModel.startRefresh()
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

        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val url = data?.data!!
                val inputStream = requireContext().contentResolver.openInputStream(url)
                if (inputStream != null) {
                    try {
                        val bufferedReader = inputStream.bufferedReader()
                        val jsonString = bufferedReader.use { it.readLine() }

                        val classData =
                            getClassFromJson(jsonString)

                        if (classData != null) {
                            viewModel.insertFromJson(classData)
                            viewModel.startInsert()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong reading the file!",
                            Toast.LENGTH_SHORT
                        ).show()

                        Timber.e("File reading failed: ${e.message}")
                    }
                }
            }
        }

    }
}