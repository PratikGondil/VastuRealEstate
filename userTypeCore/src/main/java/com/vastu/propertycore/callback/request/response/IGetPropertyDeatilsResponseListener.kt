package com.vastu.propertycore.callback.request.response

import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetPropertyDetailsResponseListener:NetworkFailedListener {
    fun getPropertyDetailsSuccessResponse(propertyDataResponseMain: PropertyDataResponseMain)
    fun getPropertyDetailsFailureResponse(propertyDataResponseMain: PropertyDataResponseMain)
}