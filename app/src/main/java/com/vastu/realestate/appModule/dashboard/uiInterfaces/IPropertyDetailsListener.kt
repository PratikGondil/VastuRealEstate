package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IPropertyDetailsListener :INetworkFailListener{
    fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
    fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
}