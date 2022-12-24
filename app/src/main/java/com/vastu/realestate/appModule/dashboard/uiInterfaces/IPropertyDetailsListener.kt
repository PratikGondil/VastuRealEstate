package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.propertycore.model.response.PropertyDataResponseMain

interface IPropertyDetailsListener {
    fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
    fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain)
}