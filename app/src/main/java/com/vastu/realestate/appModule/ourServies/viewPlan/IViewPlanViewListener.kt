package com.vastu.realestate.appModule.ourServies.viewPlan

import com.vastu.realestate.appModule.ourServies.viewPlan.response.ObjPlanResponseMain

interface IViewPlanViewListener
{
    fun onPlanSuccesss(objViewPlanResponse: ObjPlanResponseMain)
    fun onPlansFail(objViewPlanResponse: String)
}