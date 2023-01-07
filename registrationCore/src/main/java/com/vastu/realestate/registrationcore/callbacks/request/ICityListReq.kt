package com.vastu.realestate.registrationcore.callbacks.request

import android.content.Context
import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener

interface ICityListReq {
    fun callCityListApi(context: Context,urlEndPoint:String,iTalukaResponseListener: ITalukaResponseListener)
}