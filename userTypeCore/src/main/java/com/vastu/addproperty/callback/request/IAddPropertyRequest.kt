package com.vastu.addproperty.callback.request

import android.content.Context
import com.vastu.addproperty.callback.response.IAddPropertyResponseListener
import com.vastu.addproperty.model.request.AddPropertyRequest

interface IAddPropertyRequest {
    fun callAddProperty(context: Context, urlEndPoint:String,addPropertyRequest: AddPropertyRequest,iAddPropertyResponseListener: IAddPropertyResponseListener)
}