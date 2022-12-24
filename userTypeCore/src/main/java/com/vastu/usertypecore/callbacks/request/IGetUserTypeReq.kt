package com.vastu.usertypecore.callbacks.request

import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener

interface IGetUserTypeReq {
    fun callGetUserType(userId:String,urlEndPoint:String,iGetUserTypeResListener: IGetUserTypeResListener)
}