package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAdvertisementSliderListener
import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain
import com.vastu.usertypecore.repository.UserTypeRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_USER_TYPE
import com.vastu.slidercore.callback.response.IGetAdvertisementResponseListener
import com.vastu.slidercore.callback.response.IGetMainPageSliderResponseListener
import com.vastu.slidercore.model.request.MainPagerSliderRequest
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.slidercore.repository.GetAdvertisementSliderRepository
import com.vastu.slidercore.repository.MainPagerSliderRepository
import com.vastu.slidercore.repository.PropertySliderRepository

class VastuDashboardViewModel(application: Application) : AndroidViewModel(application),
    IGetUserTypeResListener,IGetAdvertisementResponseListener, IGetMainPageSliderResponseListener {

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
    fun getMainSlider(mainPagerSliderRequest: MainPagerSliderRequest){
        MainPagerSliderRepository.callMainPagerSlider(mContext,mainPagerSliderRequest,ApiUrlEndPoints.MAIN_PAGE_SLIDER,this)
    }
    fun onClickRealEstate(){
        iDashboardViewListener.onRealEstateClick()
    }
    fun onClickLoan(){
        iDashboardViewListener.onLoanClick()
    }
    fun onClickAddProperty(){
        iDashboardViewListener.onClickAddProperty()
    }

    override fun getUserTypeSuccessResponse(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        iAdvertisementSliderListener.onSuccessGetUserType(objGetUserTypeResMain)
    }

    override fun getUserTypeFailureResponse(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        iAdvertisementSliderListener.onFailGetUserType(objGetUserTypeResMain)
    }

    override fun onGetAdvertisementSuccessResponse(getAdvertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        iAdvertisementSliderListener.onSuccessAdvertisementSlider(getAdvertisementSliderMainResponse)
    }

    override fun onGetAdvertisementFailureResponse(getAdvertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
       iAdvertisementSliderListener.onFailureAdvertisementSlider(getAdvertisementSliderMainResponse)
    }

    override fun onMainPagerSliderSuccess(mainPageSliderResponse: MainPageSliderResponse) {
       iAdvertisementSliderListener.onSuccessMainSlider(mainPageSliderResponse)
    }

    override fun onMainPageSliderFailure(mainPageSliderResponse: MainPageSliderResponse) {
       iAdvertisementSliderListener.onFailureMainSlider(mainPageSliderResponse)
    }

    override fun networkFailure() {
        iAdvertisementSliderListener.onUserNotConnected()
    }
}