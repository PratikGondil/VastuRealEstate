package com.vastu.realestate.appModule.ourServies.viewPlan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PLANS
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PLANS_TYPE

class ViewPlansViewModel(application: Application) : AndroidViewModel(application),
    IViewPlanResponseListener, IPlanTypeResponseListener {
    lateinit var mContext: Application
    lateinit var iViewPlanViewListener: IViewPlanViewListener
    lateinit var iPlansTypeViewListener: IPlansTypeViewListener
    lateinit var iViewPlanListener: IViewPlanListener
    fun onBuilderPlanClick() {
        iViewPlanListener.onBuilderPlanClick()
    }

    fun onAdvertisePlanClick() {
        iViewPlanListener.onAdvertisePlanClick()
    }

    fun callPlansApi(planTypeId: String) {
        ViewPlanRepository.callPlansApi(mContext, planTypeId, GET_PLANS, this)
    }

    fun callPlansTypeApi(planTypeId: String) {
        PlanTypeRepository.callPlansTypeApi(mContext, planTypeId, GET_PLANS_TYPE, this)
    }

    override fun onGetSuccessResponse(response: ViewPlanDataResponseMain) {
        TODO("Not yet implemented")
    }

    override fun onGetFailureResponse(response: ViewPlanDataResponseMain) {
        iViewPlanViewListener.onPlansFail(response.objPlansResponse)
    }

    override fun onGetSuccessResponse(response: PlanTypeDataResponseMain) {
        TODO("Not yet implemented")
    }

    override fun onGetFailureResponse(response: PlanTypeDataResponseMain) {
        iPlansTypeViewListener.onPlansFail(response.objPlansTypeResponse)
    }

    override fun networkFailure() {
        //      iViewPlanViewListener.onUserNotConnected()
    }
}