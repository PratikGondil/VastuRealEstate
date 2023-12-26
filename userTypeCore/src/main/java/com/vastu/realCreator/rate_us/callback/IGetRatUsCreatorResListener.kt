package com.vastu.realCreator.rate_us.callback

import com.vastu.realCreator.rate_us.model.ObjCreatorRateUsRes
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetRatUsCreatorResListener: NetworkFailedListener {
    fun getRealCreatorRateUsSuccessResponse(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes)
    fun getRealCreatorRateUsFailureResponse(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes)
}