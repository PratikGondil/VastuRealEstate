package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain


interface IDashboardViewListener {
    fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain)
    fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain)
    fun onRealEstateClick()
    fun onLoanClick()
    fun onClickAddProperty()
}