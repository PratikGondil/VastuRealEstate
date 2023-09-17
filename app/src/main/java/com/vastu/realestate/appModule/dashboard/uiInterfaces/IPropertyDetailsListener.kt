package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IPropertyDetailsListener :INetworkFailListener{
    fun addPropertyEnquiry()
    fun chatEnquiry()

    fun viewbroture()
    fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
    fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
    fun onSuccessAddWishList(addWishlistResponse: AddWishlistResponse)
    fun onFailureAddWishList(addWishlistResponse: AddWishlistResponse)
    fun addToWishlist()
}