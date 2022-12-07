package com.vastu.realestate.registrationcore.callbacks.request

import com.vastu.realestate.registrationcore.callbacks.response.IResgisterResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo

interface ISignUpReq {
    fun callRegisterUserApi(objUserInfo: ObjUserInfo,urlEndPoint:String,iResgisterResponseListener: IResgisterResponseListener)
}