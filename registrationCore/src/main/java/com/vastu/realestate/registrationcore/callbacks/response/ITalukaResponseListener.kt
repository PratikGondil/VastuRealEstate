package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain

interface ITalukaResponseListener {
    fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain)
    fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain)

}