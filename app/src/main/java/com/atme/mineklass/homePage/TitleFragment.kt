package com.atme.mineklass.homePage


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentTitleBinding
import com.atme.mineklass.utils.formatTime
import com.atme.mineklass.utils.getTime
import timber.log.Timber
import java.time.ZoneId


/*
* Any feature that uses the internet is disabled for now. These features were not implemented properly
* in the first place, so I thought it would be a good idea to disable/remove them for now and come back
* to it later.
* */

class TitleFragment : Fragment() {

    private val viewModel: TitleViewModel by lazy { ViewModelProvider(this).get(TitleViewModel::class.java) }

    // TODO observe changes in data and change class accordingly
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        val adapter = TitleRecyclerAdapter()
        binding.titleRecycler.adapter = adapter

        val pref = requireActivity().getPreferences(Activity.MODE_PRIVATE)

        if (!pref.contains(requireContext().getString(R.string.data_from_internet))) {

            pref.edit().putString(
                requireContext().getString(R.string.data_from_internet),
                requireContext().getString(R.string.pref_data)
            ).apply()

            //viewModel.getFromInternet()
            viewModel.updateDay()
        } else {
            viewModel.updateDay()
        }


        viewModel.dayData.observe(viewLifecycleOwner, {
            Timber.e("Day data updated.")
            adapter.addHeaderAndSubmitList(it)
            // Start the counter with the first class in the list
            viewModel.updateClassIndex(0)
        })

        viewModel.currentClassIndex.observe(viewLifecycleOwner) {

            if (it >= viewModel.dayData.value?.size ?: 0) {
                binding.timeRemaining.text = getString(R.string.no_class_today)
            } else {
                viewModel.updateClass()
            }

        }

        viewModel.currentClass.observe(viewLifecycleOwner) {

            val startTime =
                getTime(it.time)[0].atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            viewModel.setClassTimer(startTime)
        }

        viewModel.remainingTime.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.timeRemaining.text = getString(R.string.time_remaining, formatTime(it))
            }
            else{
                binding.timeRemaining.text = getString(R.string.no_class_today)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}