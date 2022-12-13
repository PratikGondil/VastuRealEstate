package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding

class VastuDashboardFragment : Fragment(), IDashboardViewListener {
        lateinit var dashboardBinding: FragmentVastuDashboardBinding
        lateinit var vastuViewModel: VastuDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vastuViewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuDashboardViewModel = vastuViewModel
        vastuViewModel.iDashboardViewListener = this
        return dashboardBinding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(getString(R.string.vastu))
    }

    override fun onLoanClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_LoanFragment)
    }

    override fun onBackClick() {
        activity?.finishAffinity()
    }

    override fun onRealEstateClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_RealEstateFragment)
    }

}