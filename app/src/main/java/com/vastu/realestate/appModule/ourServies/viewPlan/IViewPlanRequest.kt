package com.vastu.realestate.appModule.ourServies.viewPlan

import android.content.Context

interface IViewPlanRequest {
    fun callPlansApi(context: Context, planTypeId:String, urlEndPoint:String, iViewPlanResponseListener: IViewPlanResponseListener)
}