package com.atme.mineklass.classDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atme.mineklass.databinding.FragmentClassDetailBinding


class ClassDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentClassDetailBinding.inflate(inflater, container, false)
        val selectedClass = ClassDetailFragmentArgs.fromBundle(requireArguments()).classDataArg

        binding.classData = selectedClass
        // Inflate the layout for this fragment
        return binding.root
    }


}