package com.atme.mineklass.about

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.atme.mineklass.R
import com.atme.mineklass.databinding.FragmentAboutBinding
import com.atme.mineklass.databinding.TitleHeaderBinding
import timber.log.Timber


class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var animationDrawable: AnimatedVectorDrawable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        val cLayout = binding.aboutConstraint.background

        animationDrawable = cLayout as AnimatedVectorDrawable
        animationDrawable.start()


        // Inflate the layout for this fragment
        return binding.root
    }


}