package com.atme.mineklass.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.databinding.FragmentSettingsBinding
import timber.log.Timber


class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        viewModel.refreshClassData.observe(viewLifecycleOwner, {
            if (it == true) {
                viewModel.refreshClassData()
                viewModel.doneRefresh()
                Toast.makeText(context, "Database updated!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.refreshButton.setOnClickListener {
            viewModel.startRefresh()
        }

        binding.changeButton.setOnClickListener {
            Toast.makeText(context, "To be implemented!!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}