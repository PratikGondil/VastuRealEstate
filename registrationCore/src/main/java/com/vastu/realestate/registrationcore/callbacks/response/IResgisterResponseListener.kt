package com.vastu.realestate.registrationcore.callbacks.response

import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain

interface IResgisterResponseListener {
    fun onGetSuccessResponse(objRegisterResponseMain: ObjRegisterResponseMain)
    fun onGetFailureResponse(objRegisterResponseMain: ObjRegisterResponseMain)
    fun onAlreadyExistUser(objRegisterResponseMain: ObjRegisterResponseMain)
}