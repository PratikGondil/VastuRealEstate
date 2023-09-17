package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IViewPlanResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: ViewPlanDataResponseMain)
    fun onGetFailureResponse(response: ViewPlanDataResponseMain)
}