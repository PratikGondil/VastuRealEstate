package com.vastu.realestate.logincore.callbacks.request

import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener

interface ILoginReq {
    fun callLoginApi(mobileNumber:String,urlEndPoint:String,iLoginResponseListener: ILoginResponseListener)
}