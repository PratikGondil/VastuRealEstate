package com.vastu.realestate.appModule.ourServies.planForOwner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.view.filter.SortAndFilterScreen
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler.PlanForOwnerBottomSheet
import com.vastu.realestate.databinding.PlanForOwnerFragmentBinding

class PlanForPropertyOwnerFragment:BaseFragment(),IToolbarListener,IPlanForPropertyOwner {

    lateinit var planForOwnerViewModel: PlanForOwnerViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var planForOwnerFragmentBinding: PlanForOwnerFragmentBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        planForOwnerViewModel = ViewModelProvider(this)[PlanForOwnerViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        planForOwnerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.plan_for_owner_fragment,container,false)
        planForOwnerFragmentBinding.planForOwnerViewModel = planForOwnerViewModel
        drawerViewModel.iToolbarListener = this
        planForOwnerViewModel.iPlanForPropertyOwner=this
        planForOwnerFragmentBinding.drawerViewModel= drawerViewModel
        initView()
        return planForOwnerFragmentBinding.root
    }

    fun initView(){
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        drawerViewModel.toolbarTitle.set(getString(R.string.plans_for_property_owners))
        drawerViewModel.isDashBoard.set(false)
    }

//

//
    override fun onClickBack() {
       findNavController().navigateUp()
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onClickDialog() {
     setFilterView()

    }
    private fun setFilterView() {
        try {
            val modalbottomSheetFragment = PlanForOwnerBottomSheet(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar
            )
            modalbottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                modalbottomSheetFragment.tag
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.VISIBLE
        }
        super.onPause()
    }
}