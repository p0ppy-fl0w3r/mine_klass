package com.atme.mineklass.homePage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.activity_main.*


class TitleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )



        val viewModel: TitleViewModel by lazy { ViewModelProvider(this).get(TitleViewModel::class.java) }
        val adapter = TitleRecyclerAdapter()
        binding.titleRecycler.adapter = adapter
        binding.titleCard.background = requireContext().getDrawable(R.drawable.card_rect)

        viewModel.dayData.observe(viewLifecycleOwner, {
            adapter.addHeaderAndSubmitList(it)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}