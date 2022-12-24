package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain
import com.vastu.usertypecore.repository.UserTypeRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_USER_TYPE

class VastuDashboardViewModel(application: Application) : AndroidViewModel(application),
    IGetUserTypeResListener {

    lateinit var iDashboardViewListener : IDashboardViewListener

    fun getUserType(userId:String){
        UserTypeRepository.callGetUserType(userId,GET_USER_TYPE ,this)
    }
    fun onClickRealEstate(){
        iDashboardViewListener.onRealEstateClick()
    }
    fun onClickLoan(){
        iDashboardViewListener.onLoanClick()
    }

    override fun getUserTypeSuccessResponse(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        iDashboardViewListener.onSuccessGetUserType(objGetUserTypeResMain)
    }

    override fun getUserTypeFailureResponse(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        iDashboardViewListener.onFailGetUserType(objGetUserTypeResMain)
    }

}