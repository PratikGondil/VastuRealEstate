package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain

interface IAdvertisementSliderListener :INetworkFailListener{
    fun onSuccessAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse)
    fun onFailureAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse)
    fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain)
    fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain)
    fun onSuccessMainSlider(mainPageSliderResponse: MainPageSliderResponse)
    fun onFailureMainSlider(mainPageSliderResponse: MainPageSliderResponse)
}