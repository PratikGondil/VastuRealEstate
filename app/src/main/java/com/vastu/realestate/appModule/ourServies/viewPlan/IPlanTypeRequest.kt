package com.vastu.realestate.appModule.ourServies.viewPlan

import android.content.Context

interface IPlanTypeRequest {
    fun callPlansTypeApi(context: Context,    language: String,
                         planTypeId: String,
                         profileId:String, urlEndPoint:String, iPlanTypeResponseListener: IPlanTypeResponseListener)
}