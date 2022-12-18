package com.vastu.realestate.registrationcore.callbacks.request

import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener

interface ICityListReq {
    fun callCityListApi(urlEndPoint:String,iTalukaResponseListener: ITalukaResponseListener)
}