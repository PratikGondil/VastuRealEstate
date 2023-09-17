package com.vastu.realestate.appModule.ourServies.viewPlan

import android.content.Context

interface IPlanTypeRequest {
    fun callPlansTypeApi(context: Context, planTypeId: String, urlEndPoint:String, iPlanTypeResponseListener: IPlanTypeResponseListener)
}