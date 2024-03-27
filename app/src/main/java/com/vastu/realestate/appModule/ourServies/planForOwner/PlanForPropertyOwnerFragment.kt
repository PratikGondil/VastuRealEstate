package com.vastu.realestate.appModule.ourServies.planForOwner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler.PlanForOwnerBottomSheet
import com.vastu.realestate.appModule.ourServies.planForOwner.response.ObjPlanByTypeResponseMain
import com.vastu.realestate.appModule.ourServies.viewPlan.IPlansByTypeViewListener
import com.vastu.realestate.databinding.PlanForOwnerFragmentBinding
import com.vastu.realestate.utils.PreferenceManger


class PlanForPropertyOwnerFragment:BaseFragment(),IToolbarListener,IPlanForPropertyOwner,IPlansByTypeViewListener,IClickPlanCheck {

    lateinit var planForOwnerViewModel: PlanForOwnerViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var planForOwnerFragmentBinding: PlanForOwnerFragmentBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var recyclerAdapterPlans: RecyclerAdapterPlans
    lateinit var objPlansTypeResponseCopy: ObjPlanByTypeResponseMain


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
        planForOwnerViewModel.iPlansTypeViewListener= this
        planForOwnerFragmentBinding.drawerViewModel= drawerViewModel
        initView()
        callPlanTypeAPI()
        return planForOwnerFragmentBinding.root
    }

    private fun callPlanTypeAPI() {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        planForOwnerViewModel.callPlansTypeApi(language!!,"1","")
    }

    fun initView(){
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        drawerViewModel.toolbarTitle.set(getString(R.string.plans_for_property_owners))
        drawerViewModel.isDashBoard.set(false)

        //grid layout manager
        planForOwnerFragmentBinding.rvRecyleview.layoutManager = GridLayoutManager(requireContext(), 2)
        planForOwnerFragmentBinding.rvPlans.layoutManager = GridLayoutManager(requireContext(), 2)

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

    private fun benifitisArray(objPlansTypeResponse: ObjPlanByTypeResponseMain) {
        val myArrayList = ArrayList<String>()

    }

    override fun onPlanSuccess(objPlansTypeResponse: ObjPlanByTypeResponseMain) {
        objPlansTypeResponseCopy = objPlansTypeResponse
        setView(objPlansTypeResponse)
        setPlanDescription("0")

    }

    private fun setView(objPlansTypeResponse: ObjPlanByTypeResponseMain) {

        recyclerAdapterPlans = RecyclerAdapterPlans(requireContext(), objPlansTypeResponse.GetPlanDetailsResponse.planData,this)
        planForOwnerFragmentBinding.rvPlans.adapter = recyclerAdapterPlans
    }

    override fun onPlansFail(objPlansTypeResponse: ObjPlanByTypeResponseMain) {

    }

    override fun selectedPlan(position: String) {
       setPlanDescription(position)
    }

    private fun setPlanDescription(position: String) {
        recyclerAdapter = RecyclerAdapter(requireContext(), objPlansTypeResponseCopy.GetPlanDetailsResponse.planData.get(position.toInt()).benefits)
        planForOwnerFragmentBinding.rvRecyleview.adapter = recyclerAdapter
    }
}