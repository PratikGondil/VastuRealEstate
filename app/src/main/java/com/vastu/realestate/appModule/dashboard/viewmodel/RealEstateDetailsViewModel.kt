package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.callback.request.response.IGetWishlistResponseListener
import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.propertycore.repository.AddWishlistRepository
import com.vastu.propertycore.repository.PropertyDetailsRepository
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.utils.ApiUrlEndPoints.ADD_WISHLIST
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY
import com.vastu.realestate.utils.ApiUrlEndPoints.PROPERTY_SLIDER
import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.slidercore.model.response.realestatedetails.PropertyDetailsResponseSliderMain
import com.vastu.slidercore.repository.PropertySliderRepository

class RealEstateDetailsViewModel(application: Application) : AndroidViewModel(application),
    IGetPropertyDetailsResponseListener,
    IGetPropertySliderByIdResponse,
    IGetWishlistResponseListener {
    lateinit var iPropertyDetailsListener: IPropertyDetailsListener
    lateinit var iPropertySliderListener: IPropertySliderListener

    var mContext :Application
    init {
        mContext = application
    }
    fun onClickPropertyEnquiry(){
        iPropertyDetailsListener.addPropertyEnquiry()
    }

    fun onClickViewBrocture()
    {
    iPropertyDetailsListener.viewbroture()
    }
    fun onClickChat(){
        iPropertyDetailsListener.chatEnquiry()
    }

    fun getPropertySlider(propertyId:String){
        PropertySliderRepository.callGetPropertySliderById(mContext,propertyId,PROPERTY_SLIDER,this)
    }

    fun getPropertyDetails(userId:String,propertyId:String){
        PropertyDetailsRepository.callGetPropertyDetails(mContext,userId,propertyId,GET_PROPERTY,this)
    }

    fun getAddToWishlist(userId:String,propertyId:String){
        AddWishlistRepository.callAddWishlistApi(mContext,userId,propertyId,
            ADD_WISHLIST,this)
    }
    override fun getPropertyDetailsSuccessResponse(propertyDataResponseMain: PropertyDataResponseMain) {
      iPropertyDetailsListener.onSuccessGetPropertyDetails(propertyDataResponseMain)
    }

    override fun getPropertyDetailsFailureResponse(propertyDataResponseMain: PropertyDataResponseMain) {
        iPropertyDetailsListener.onFailureGetPropertyDetails(propertyDataResponseMain)
    }

    override fun getPropertySliderByIdSuccessResponse(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
        iPropertySliderListener.onSuccessPropertySliderById(propertySliderResponseMain)
    }

    override fun getPropertySliderByIdFailureResponse(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
       iPropertySliderListener.onFailurePropertySliderById(propertySliderResponseMain)
    }

    override fun getWishlistSuccessResponse(addWishlistResponse: AddWishlistResponse) {
      iPropertyDetailsListener.onSuccessAddWishList(addWishlistResponse)
    }

    override fun getWishlistFailureResponse(addWishlistResponse: AddWishlistResponse) {
        iPropertyDetailsListener.onFailureAddWishList(addWishlistResponse)
    }

    override fun networkFailure() {
        iPropertyDetailsListener.onUserNotConnected()
        iPropertySliderListener.onUserNotConnected()
    }

    fun addToWishlist(){
        iPropertyDetailsListener.addToWishlist()
    }
    fun onClickBuilderProfile()
    {
        iPropertyDetailsListener.onClickBuilderProfile()
    }
}