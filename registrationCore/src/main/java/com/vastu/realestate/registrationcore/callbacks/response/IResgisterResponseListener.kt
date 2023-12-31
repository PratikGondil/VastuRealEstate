package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IResgisterResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(objRegisterResponseMain: ObjRegisterResponseMain)
    fun onGetFailureResponse(objRegisterResponseMain: ObjRegisterResponseMain)
    fun onAlreadyExistUser(objRegisterResponseMain: ObjRegisterResponseMain)
}