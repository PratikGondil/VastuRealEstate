package com.vastu.realCreator.rate_us.callback

import com.vastu.realCreator.rate_us.model.CreatorRateUsRes
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetRatUsCreatorResListener: NetworkFailedListener {
    fun getRealCreatorRateUsSuccessResponse(objcreatorRateUsRes: CreatorRateUsRes)
    fun getRealCreatorRateUsFailureResponse(objcreatorRateUsRes: CreatorRateUsRes)
}