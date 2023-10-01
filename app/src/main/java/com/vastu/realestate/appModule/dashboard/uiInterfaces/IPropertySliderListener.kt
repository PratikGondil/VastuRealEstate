package com.vastu.realestate.appModule.dashboard.uiInterfaces


import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.slidercore.model.response.realestatedetails.PropertyDetailsResponseSliderMain

interface IPropertySliderListener:INetworkFailListener{
    fun onSuccessPropertySliderById(propertySliderResponseMain: PropertyDetailsResponseSliderMain)
    fun onFailurePropertySliderById(propertySliderResponseMain: PropertyDetailsResponseSliderMain)
}