package com.atme.mineklass.classDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atme.mineklass.databinding.FragmentClassDetailBinding


class ClassDetailFragment : Fragment() {

    private val viewModel: ClassDetailViewModel by lazy {
        ViewModelProvider(this)[ClassDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentClassDetailBinding.inflate(inflater, container, false)
        val selectedClass = ClassDetailFragmentArgs.fromBundle(requireArguments()).classDataArg

        binding.classData = selectedClass

        binding.editClassDetail.setOnClickListener {
            findNavController().navigate(ClassDetailFragmentDirections.detailsToEdit(selectedClass.id.toString()))
        }

        binding.deleteClass.setOnClickListener{
            viewModel.deleteClass(selectedClass)
            findNavController().navigate(ClassDetailFragmentDirections.detailsToSchedule())
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}