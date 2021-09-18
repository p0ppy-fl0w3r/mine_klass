package com.atme.mineklass.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atme.mineklass.Constants
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentScheduleBinding>(
            inflater,
            R.layout.fragment_schedule,
            container,
            false
        )
        val viewModel: ScheduleViewModel by lazy {
            ViewModelProvider(this).get(ScheduleViewModel::class.java)
        }


        val recycler = binding.recycler
        val adapter = ScheduleAdapter(OnClickListener{
            viewModel.navigateToClassDetail(it)
        })

        viewModel.classLiveData.observe(viewLifecycleOwner, { classList ->
            classList?.let { nClassList ->
                val sortedList = nClassList.sortedBy { it.getValue() }
                adapter.submitList(sortedList)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, {
            if(it != null){
                findNavController().navigate(ScheduleFragmentDirections.actionNavigateToDetails(it))
                viewModel.doneNavigatingToDetail()
            }
        })

        recycler.adapter = adapter

        binding.addClassFab.setOnClickListener{
            findNavController().navigate(ScheduleFragmentDirections.scheduleToNew(Constants.ID_EDIT))
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}