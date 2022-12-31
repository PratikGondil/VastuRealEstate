package com.vastu.slidercore.callback.response

import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetPropertySliderByIdResponse :NetworkFailedListener{
    fun getPropertySliderByIdSuccessResponse(propertySliderResponseMain: PropertySliderResponseMain)
    fun getPropertySliderByIdFailureResponse(propertySliderResponseMain: PropertySliderResponseMain)
}