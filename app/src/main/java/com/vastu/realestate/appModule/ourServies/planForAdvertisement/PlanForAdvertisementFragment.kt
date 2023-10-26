package com.vastu.realestate.appModule.ourServies.planForAdvertisement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.PlanForAdvertisementFragmentBinding

class PlanForAdvertisementFragment: BaseFragment(), IToolbarListener, IPlanForAdvertisementViewListener {
    lateinit var planForAdvertisementViewModel: PlanForAdvertisementViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var planForAdvertisementFragmentBinding: PlanForAdvertisementFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        planForAdvertisementViewModel = ViewModelProvider(this)[PlanForAdvertisementViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        planForAdvertisementFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.plan_for_advertisement_fragment,container,false)
        planForAdvertisementFragmentBinding.planForAdvertisementViewModel = planForAdvertisementViewModel
        drawerViewModel.iToolbarListener = this
        planForAdvertisementViewModel.iPlanForAdvertisementViewListener = this
        planForAdvertisementFragmentBinding.drawerViewModel= drawerViewModel
        initView()
        return planForAdvertisementFragmentBinding.root
    }

    private fun initView() {
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        drawerViewModel.toolbarTitle.set(getString(R.string.plan_for_advertisement))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        TODO("Not yet implemented")
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onViewPlanBtnClick() {
        findNavController().navigate(R.id.action_planForAdvertisementFragment_to_modeForAdvertisementFragment)
    }
}