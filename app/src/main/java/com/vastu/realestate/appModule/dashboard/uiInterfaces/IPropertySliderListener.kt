package com.vastu.realestate.appModule.dashboard.uiInterfaces


import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain

interface IPropertySliderListener:INetworkFailListener{
    fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain)
    fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain)
}