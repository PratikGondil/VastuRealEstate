package com.vastu.realestate.logincore.callbacks.request

import android.content.Context
import com.vastu.realestate.logincore.callbacks.response.ILoginResponseListener

interface ILoginReq {
    fun callLoginApi(context: Context,mobileNumber:String,language:String,urlEndPoint:String,iLoginResponseListener: ILoginResponseListener)
}