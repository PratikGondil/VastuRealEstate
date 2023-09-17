package com.vastu.realestate.appModule.rateUs

import android.content.Context

interface IRateUsRequest {
    fun callRateUsApi(context: Context, userId: String, rateUs: String, feedback: String, urlEndPoint:String, iRateUsResponseListener: IRateUsResponseListener)
}