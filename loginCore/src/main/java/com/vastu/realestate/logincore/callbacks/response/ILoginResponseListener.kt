package com.vastu.realestate.logincore.callbacks.response

import com.vastu.realestate.logincore.model.response.ObjLoginResponse

interface ILoginResponseListener {
    fun onGetSuccessResponse(response: ObjLoginResponse)
    fun onGetFailureResponse(response: ObjLoginResponse)
}