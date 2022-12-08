package com.vastu.realestate.appModule.dashboard.callback

import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts

interface IDashboardViewListener {
    fun onMenuClick()
    fun onNotificationClick()
    fun onLoanClick()
    fun onRealEstateClick()
}