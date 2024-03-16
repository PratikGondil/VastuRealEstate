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
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler.PlanForOwnerBottomSheet
import com.vastu.realestate.appModule.ourServies.viewPlan.response.ObjPlanResponseMain
import com.vastu.realestate.appModule.rateUs.RateUsFragment
import com.vastu.realestate.databinding.ViewPlansFragmentBinding
import com.vastu.realestate.utils.PreferenceManger


class OurServicesFragment : Fragment(), IToolbarListener, IViewPlanListener,IViewPlanViewListener{
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
        viewPlansViewModel.iViewPlanViewListener= this
        viewPlansFragmentBinding.drawerViewModel = drawerViewModel
        initView()
        return viewPlansFragmentBinding.root
    }

    fun initView() {
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        drawerViewModel.toolbarTitle.set(getString(R.string.our_services_text))
        drawerViewModel.isDashBoard.set(false)
        callPlansAPI()
    }

    override fun onClickBack() {
        findNavController().navigateUp()
    }

    fun callPlansAPI(){
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        viewPlansViewModel.callPlansApi(language!!)

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
        findNavController().navigate(R.id.planForAdvertisementFragment)
       // viewPlansViewModel.callPlansApi(planTypeId!!)
    }

    override fun onPlanSuccesss(objViewPlanResponse: ObjPlanResponseMain) {
        setView(objViewPlanResponse)
    }

    private fun setView(objViewPlanResponse: ObjPlanResponseMain) {
        viewPlansFragmentBinding.apply {
            txtPlanBuilder.text = objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(0).plan_type
            txtInfo.text =objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(0).description
            PreferenceManger.putString<String>(Constants.TERMS_BUILDER,objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(0).terms)

            txtPlanAdd.text= objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(1).plan_type
            txtInfo2.text =objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(1).description
            PreferenceManger.putString<String>(Constants.TERMS_Advertisment,objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(1).terms)

            txtPlanCreator.text= objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(2).plan_type
            txtInfo3.text =objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(2).description
            PreferenceManger.putString<String>(Constants.TERMS_REAL_CREATOR,objViewPlanResponse.GetPlanDetailsResponse.PlanData.get(2).terms)


        }

    }

    override fun onPlansFail(objViewPlanResponse: String) {
        //TODO("Not yet implemented")
    }

//    private fun getPlans() {
//        planTypeId?.let {
//            viewPlansViewModel.callPlansApi(it)
//        }
//    }

}