package com.vastu.offers.callbacks.response

import com.vastu.offers.model.response.OffersMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetOffersResponseListener: NetworkFailedListener {
    fun getOffersSuccess(offersMainResponse: OffersMainResponse)
    fun getOffersFailure(offersMainResponse: OffersMainResponse)
}