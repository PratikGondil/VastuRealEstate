package com.vastu.usertypecore.callbacks.request

import android.content.Context
import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener

interface IGetUserTypeReq {
    fun callGetUserType(context: Context,userId:String,urlEndPoint:String,iGetUserTypeResListener: IGetUserTypeResListener)
}