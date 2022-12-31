package com.vastu.realestatecore.callback.response

import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetPropertyListResListener:NetworkFailedListener {
    fun getPropertyListSuccessResponse(objGetPropertyListResMain: ObjGetPropertyListResMain)
    fun getPropertyListFailureResponse(objGetPropertyListResMain: ObjGetPropertyListResMain)
}