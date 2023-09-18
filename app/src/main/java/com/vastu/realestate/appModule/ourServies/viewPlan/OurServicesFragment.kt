package com.vastu.realestate.appModule.ourServies.viewPlan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler.PlanForOwnerBottomSheet
import com.vastu.realestate.appModule.rateUs.RateUsFragment
import com.vastu.realestate.databinding.ViewPlansFragmentBinding


class OurServicesFragment : Fragment(), IToolbarListener, IViewPlanListener {
    lateinit var viewPlansViewModel: ViewPlansViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var viewPlansFragmentBinding: ViewPlansFragmentBinding

    companion object {
        var planTypeId: String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPlansViewModel = ViewModelProvider(this)[ViewPlansViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        viewPlansFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_plans_fragment, container, false)
        viewPlansFragmentBinding.viewPlansViewModel = viewPlansViewModel
        drawerViewModel.iToolbarListener = this
        viewPlansViewModel.iViewPlanListener = this
        viewPlansFragmentBinding.drawerViewModel = drawerViewModel
        initView()
        return viewPlansFragmentBinding.root
    }

    fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.our_services_text))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        findNavController().navigateUp()
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onBuilderPlanClick() {
        findNavController().navigate(R.id.planForOwnerFragment)
    }

    override fun onAdvertisePlanClick() {
        viewPlansViewModel.callPlansApi(planTypeId!!)
    }

//    private fun getPlans() {
//        planTypeId?.let {
//            viewPlansViewModel.callPlansApi(it)
//        }
//    }

}