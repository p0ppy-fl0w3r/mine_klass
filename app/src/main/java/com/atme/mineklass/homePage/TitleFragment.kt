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
import androidx.viewpager2.widget.ViewPager2
import com.atme.mineklass.Constants
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentTitleBinding
import com.atme.mineklass.utils.formatTime
import com.atme.mineklass.utils.getTime
import timber.log.Timber
import java.time.ZoneId
import java.util.*


/*
* Any feature that uses the internet is disabled for now. These features were not implemented properly
* in the first place, so I thought it would be a good idea to disable/remove them for now and come back
* to it later.
* */

class TitleFragment : Fragment() {

    private val viewModel: TitleViewModel by lazy { ViewModelProvider(this)[TitleViewModel::class.java] }

    private lateinit var binding: FragmentTitleBinding


    private var currentPage:Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        if (savedInstanceState != null){
            currentPage = savedInstanceState.getInt("currentPage", 0)
            binding.classPager.currentItem = currentPage
        }


        val viewPager = binding.classPager

        val slideAdapter = ClassSlideAdapter()
        viewPager.adapter = slideAdapter

        // TODO migrate to datastore
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

        viewModel.getAllClassData()

        viewModel.allClassData.observe(viewLifecycleOwner){
            slideAdapter.submitList(it)
            currentPage = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
            viewPager.currentItem = currentPage
        }

        viewModel.dayData.observe(viewLifecycleOwner) {
            Timber.i("Day data updated.")
            // Start the counter with the first class in the list
            viewModel.updateClassIndex(0)
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentPage", currentPage)
    }
}