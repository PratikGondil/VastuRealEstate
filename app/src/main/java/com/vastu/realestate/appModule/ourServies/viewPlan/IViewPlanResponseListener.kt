package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.realestate.appModule.ourServies.viewPlan.response.ObjPlanResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IViewPlanResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: ObjPlanResponseMain)
    fun onGetFailureResponse(response: ObjPlanResponseMain)
}