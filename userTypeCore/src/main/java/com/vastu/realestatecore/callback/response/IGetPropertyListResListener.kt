package com.vastu.realestatecore.callback.response

import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain

interface IGetPropertyListResListener {
    fun getPropertyListSuccessResponse(objGetPropertyListResMain: ObjGetPropertyListResMain)
    fun getPropertyListFailureResponse(objGetPropertyListResMain: ObjGetPropertyListResMain)
}