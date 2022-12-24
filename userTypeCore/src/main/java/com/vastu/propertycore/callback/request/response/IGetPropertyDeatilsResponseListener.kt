package com.vastu.propertycore.callback.request.response

import com.vastu.propertycore.model.response.PropertyDataResponseMain

interface IGetPropertyDetailsResponseListener {
    fun getPropertyDetailsSuccessResponse(propertyDataResponseMain: PropertyDataResponseMain)
    fun getPropertyDetailsFailureResponse(propertyDataResponseMain: PropertyDataResponseMain)
}