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
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanViewModel
import com.vastu.realestate.databinding.FragmentLoanBinding

class LoanFragment : Fragment(), IBackClickListener {

    private lateinit var loanBinding: FragmentLoanBinding
    private lateinit var loanViewModel: LoanViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loanViewModel = ViewModelProvider(this)[LoanViewModel::class.java]
        loanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan, container, false)
        loanBinding.lifecycleOwner = this
        loanBinding.loanViewModel = loanViewModel
        loanViewModel.iBackClickListener = this
        return loanBinding.root
    }

    override fun onBackClick() {
       findNavController().navigate(R.id.action_LoanFragment_to_VastuDashboardFragment)
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(getString(R.string.loan))
    }


}