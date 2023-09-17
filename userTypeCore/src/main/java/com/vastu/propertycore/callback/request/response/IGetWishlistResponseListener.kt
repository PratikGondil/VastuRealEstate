package com.vastu.propertycore.callback.request.response

import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetWishlistResponseListener:NetworkFailedListener {
    fun getWishlistSuccessResponse(addWishlistResponse: AddWishlistResponse)
    fun getWishlistFailureResponse(addWishlistResponse: AddWishlistResponse)
}