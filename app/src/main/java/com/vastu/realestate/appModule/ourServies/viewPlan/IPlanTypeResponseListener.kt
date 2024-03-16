package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.realestate.appModule.ourServies.planForOwner.response.ObjPlanByTypeResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IPlanTypeResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: ObjPlanByTypeResponseMain)
    fun onGetFailureResponse(response: ObjPlanByTypeResponseMain)
}