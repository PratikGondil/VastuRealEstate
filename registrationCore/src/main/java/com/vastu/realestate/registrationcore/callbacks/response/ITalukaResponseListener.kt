package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface ITalukaResponseListener : NetworkFailedListener{
    fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain)
    fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain)

}