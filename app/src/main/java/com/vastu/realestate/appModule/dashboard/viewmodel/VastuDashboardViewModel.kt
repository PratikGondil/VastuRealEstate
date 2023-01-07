package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAdvertisementSliderListener
import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain
import com.vastu.usertypecore.repository.UserTypeRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_USER_TYPE
import com.vastu.slidercore.callback.response.IGetAdvertisementResponseListener
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.slidercore.repository.GetAdvertisementSliderRepository
import com.vastu.slidercore.repository.PropertySliderRepository

class VastuDashboardViewModel(application: Application) : AndroidViewModel(application),
    IGetUserTypeResListener,IGetAdvertisementResponseListener {

    lateinit var iDashboardViewListener : IDashboardViewListener
    lateinit var iAdvertisementSliderListener: IAdvertisementSliderListener

    var mContext :Application
    init {
        mContext = application
    }

    fun getUserType(userId:String){
        UserTypeRepository.callGetUserType(mContext,userId,GET_USER_TYPE ,this)
    }
    fun getAdvertisementSlider(){
        GetAdvertisementSliderRepository.callGetAdvertisementSlider(mContext,ApiUrlEndPoints.GET_ADVERTISEMENT_SLIDER,this)
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

    override fun onGetAdvertisementSuccessResponse(getAdvertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        iAdvertisementSliderListener.onSuccessAdvertisementSlider(getAdvertisementSliderMainResponse)
    }

    override fun onGetAdvertisementFailureResponse(getAdvertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
       iAdvertisementSliderListener.onFailureAdvertisementSlider(getAdvertisementSliderMainResponse)
    }

    override fun networkFailure() {
        iAdvertisementSliderListener.onUserNotConnected()
    }
}