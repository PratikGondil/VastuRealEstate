package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.realestate.appModule.ourServies.planForOwner.response.ObjPlanByTypeResponseMain

interface IPlansByTypeViewListener {
    fun onPlanSuccess(objPlansTypeResponse: ObjPlanByTypeResponseMain)
    fun onPlansFail(objPlansTypeResponse: ObjPlanByTypeResponseMain)
}