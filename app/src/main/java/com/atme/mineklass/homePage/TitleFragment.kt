package com.atme.mineklass.homePage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentTitleBinding
import com.atme.mineklass.utils.formatTime
import com.atme.mineklass.utils.getTime
import timber.log.Timber
import java.time.ZoneId


class TitleFragment : Fragment() {
    private val viewModel: TitleViewModel by lazy { ViewModelProvider(this).get(TitleViewModel::class.java) }


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
        binding.titleCard.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.card_rect)



        viewModel.dayData.observe(viewLifecycleOwner, {
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
            binding.timeRemaining.text = getString(R.string.time_remaining, formatTime(it))
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}