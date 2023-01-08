package com.vastu.editproperty.callbacks.request

import android.content.Context
import com.vastu.editproperty.callbacks.response.IEditPropertyResponseListener
import com.vastu.editproperty.model.request.EditPropertyRequest

interface IEditPropertyRequest {
    fun callEditProperty(context: Context, urlEndPoint:String,editPropertyRequest: EditPropertyRequest,iEditPropertyResponseListener: IEditPropertyResponseListener)
}