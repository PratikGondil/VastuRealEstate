package com.vastu.realestate.logincore.callbacks.response

import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface ILoginResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: ObjLoginResponseMain)
    fun onGetFailureResponse(response: ObjLoginResponseMain)
}