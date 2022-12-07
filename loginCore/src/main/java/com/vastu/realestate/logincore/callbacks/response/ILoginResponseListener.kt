package com.vastu.realestate.logincore.callbacks.response

import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain

interface ILoginResponseListener {
    fun onGetSuccessResponse(response: ObjLoginResponseMain)
    fun onGetFailureResponse(response: ObjLoginResponseMain)
}