package com.vastu.realestate.appModule.signUp.uiInterfaces

import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts
import com.vastu.realestate.registrationcore.model.response.ObjRegisterResponseMain

interface ISignUpViewListener {
    fun registerUser()
    fun launchOtpScreen(objRegisterDlts:ObjRegisterDlts)
    fun goToLogin()
}