package com.vastu.slidercore.callback.response

import com.vastu.slidercore.model.response.PropertySliderResponseMain

interface IGetPropertySliderByIdResponse {
    fun getPropertySliderByIdSuccessResponse(propertySliderResponseMain: PropertySliderResponseMain)
    fun getPropertySliderByIdFailureResponse(propertySliderResponseMain: PropertySliderResponseMain)
}