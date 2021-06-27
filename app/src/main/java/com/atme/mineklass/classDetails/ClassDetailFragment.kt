package com.atme.mineklass.classDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentClassDetailBinding


class ClassDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentClassDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this


        val selectedClass = ClassDetailFragmentArgs.fromBundle(requireArguments()).classDataArg

        // TODO Remove comment
        // Used viewModel so that the class data can be edited in the future.
        val viewModelFactory = ClassDetailViewModelFactory(selectedClass)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ClassDetailViewModel::class.java)
        binding.viewModel = viewModel
        // Inflate the layout for this fragment
        return binding.root
    }


}