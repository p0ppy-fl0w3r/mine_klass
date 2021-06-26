package com.atme.mineklass.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atme.mineklass.R
import com.atme.mineklass.classData.ClassData
import com.atme.mineklass.databinding.FragmentScheduleBinding
import timber.log.Timber
import java.time.LocalDate
import java.util.*


class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        viewModel.classLiveData.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, {
            if(it != null){
                findNavController().navigate(ScheduleFragmentDirections.actionNavigateToDetails(it))
                viewModel.doneNavigatingToDetail()
            }
        })

        recycler.adapter = adapter
        // Inflate the layout for this fragment
        return binding.root
    }
}