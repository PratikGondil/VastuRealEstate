package com.vastu.usertypecore.callbacks.response

import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain

interface IGetUserTypeResListener :NetworkFailedListener{
    fun getUserTypeSuccessResponse(objGetUserTypeResMain: ObjGetUserTypeResMain)
    fun getUserTypeFailureResponse(objGetUserTypeResMain: ObjGetUserTypeResMain)
}