package com.vastu.realestate.appModule.rateUs

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R

import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.employee.bindingAdapter.EmployeeDetailsBindingAdapter.setRating
import com.vastu.realestate.appModule.feedback.FeedbackFragment

import com.vastu.realestate.databinding.RateUsFragmentBinding

class RateUsFragment: BaseFragment(), IToolbarListener, IRateUsViewListener {

    lateinit var rateUsViewModel: RateUsViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var rateUsBinding: RateUsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rateUsViewModel = ViewModelProvider(this)[RateUsViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        rateUsBinding = DataBindingUtil.inflate(inflater, R.layout.rate_us_fragment,container,false)
        drawerViewModel.iToolbarListener = this
        rateUsViewModel.iRateUsViewListener = this
        rateUsBinding.drawerViewModel= drawerViewModel
        rateUsBinding.rateUsViewModel = rateUsViewModel
        initView()
        return rateUsBinding.root
    }

    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.rate_us))
        drawerViewModel.isDashBoard.set(false)
    }
    override fun onClickBack() {
        findNavController().navigate(R.id.action_rateUs_to_Dashboard)
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onRateUsFail(objRateUsResponse: ObjRateUsResponse) {
        hideProgressDialog()
        showDialog(objRateUsResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun launchDashboardScreen(rateUsDataResponseMain: RateUsDataResponseMain) {
        showDialog(rateUsDataResponseMain.objRateUsResponse.objResponseStatusHdr.statusDescr,true,false)
        Handler().postDelayed({
            hideDialog()
        }, 750)
    }

    override fun onSubmitBtnClick() {
        rateUsViewModel.callRateUsApi(DashboardFragment.userId!!,
            rateUsBinding.smallRating.toString(),rateUsBinding.edtReview.text.toString())

    }


}