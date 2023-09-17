package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IPlanTypeResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: PlanTypeDataResponseMain)
    fun onGetFailureResponse(response: PlanTypeDataResponseMain)
}