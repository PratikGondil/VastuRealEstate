package com.vastu.realestate.registrationcore.callbacks.request

import android.content.Context
import com.vastu.realestate.registrationcore.callbacks.response.IResgisterResponseListener
import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo

interface ISignUpReq {
    fun callRegisterUserApi(context: Context,objUserInfo: ObjUserInfo,urlEndPoint:String,iResgisterResponseListener: IResgisterResponseListener)
}