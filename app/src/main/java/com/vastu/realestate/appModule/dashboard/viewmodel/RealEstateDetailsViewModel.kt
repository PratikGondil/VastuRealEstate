package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.propertycore.repository.PropertyDetailsRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY
import com.vastu.realestate.utils.ApiUrlEndPoints.PROPERTY_SLIDER
import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.slidercore.repository.PropertySliderRepository

class RealEstateDetailsViewModel(application: Application) : AndroidViewModel(application),
    IGetPropertyDetailsResponseListener,
    IGetPropertySliderByIdResponse{

    lateinit var iPropertyDetailsListener: IPropertyDetailsListener
    lateinit var iPropertySliderListener: IPropertySliderListener

    var mContext :Application
    init {
        mContext = application
    }

    fun getPropertySlider(propertyId:String){
        PropertySliderRepository.callGetPropertySliderById(mContext,propertyId,PROPERTY_SLIDER,this)
    }

    fun getPropertyDetails(userId:String,propertyId:String){
        PropertyDetailsRepository.callGetPropertyDetails(mContext,userId,propertyId,GET_PROPERTY,this)
    }

    override fun getPropertyDetailsSuccessResponse(propertyDataResponseMain: PropertyDataResponseMain) {
      iPropertyDetailsListener.onSuccessGetPropertyDetails(propertyDataResponseMain)
    }

    override fun getPropertyDetailsFailureResponse(propertyDataResponseMain: PropertyDataResponseMain) {
        iPropertyDetailsListener.onFailureGetPropertyDetails(propertyDataResponseMain)
    }

    override fun getPropertySliderByIdSuccessResponse(propertySliderResponseMain: PropertySliderResponseMain) {
        iPropertySliderListener.onSuccessPropertySliderById(propertySliderResponseMain)
    }

    override fun getPropertySliderByIdFailureResponse(propertySliderResponseMain: PropertySliderResponseMain) {
       iPropertySliderListener.onFailurePropertySliderById(propertySliderResponseMain)
    }

    override fun networkFailure() {
        iPropertyDetailsListener.onUserNotConnected()
        iPropertySliderListener.onUserNotConnected()
    }
}