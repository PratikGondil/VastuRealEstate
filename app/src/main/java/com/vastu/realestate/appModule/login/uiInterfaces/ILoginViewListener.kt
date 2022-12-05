package com.vastu.realestate.appModule.login.uiInterfaces

import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts

interface ILoginViewListener {
    fun onSendOtpClick()
    fun launchOtpScreen(objLoginResponse: ObjLoginResponse)
}