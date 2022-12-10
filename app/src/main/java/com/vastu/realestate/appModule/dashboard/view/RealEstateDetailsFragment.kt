package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IBackClickListener
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.databinding.FragmentRealEstateDetailsBinding

class RealEstateDetailsFragment : Fragment() , IBackClickListener {
    private lateinit var realEstateDetailsBinding: FragmentRealEstateDetailsBinding
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateDetailsViewModel = ViewModelProvider(this)[RealEstateDetailsViewModel::class.java]
        realEstateDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate_details, container, false)
        realEstateDetailsBinding.lifecycleOwner = this
        realEstateDetailsBinding.realEstateDetailsViewModel = realEstateDetailsViewModel
        realEstateDetailsViewModel.iBackClickListener = this
        return realEstateDetailsBinding.root
    }

    override fun onBackClick() {
       findNavController().navigate(R.id.action_RealEstateDetailsFragment_to_RealEstateFragment)
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(getString(R.string.real_estate))
    }
}