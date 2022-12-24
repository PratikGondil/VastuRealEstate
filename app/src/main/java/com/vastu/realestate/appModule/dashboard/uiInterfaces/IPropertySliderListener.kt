package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.slidercore.model.response.PropertySliderResponseMain

interface IPropertySliderListener {
    fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain)
    fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain)
}