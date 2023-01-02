package com.vastu.realestate.appModule.login.uiInterfaces

import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain

interface ILoginViewListener : INetworkFailListener {
    fun onSendOtpClick()
    fun launchOtpScreen(objLoginResponseMain: ObjLoginResponseMain)
    fun onLoginFail(objLoginResponse: ObjLoginResponse)
}