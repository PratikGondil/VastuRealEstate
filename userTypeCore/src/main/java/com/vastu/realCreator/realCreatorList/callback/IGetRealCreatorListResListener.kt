package com.vastu.realCreator.realCreatorList.callback

import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetRealCreatorListResListener: NetworkFailedListener {
    fun getRealCreatorListSuccessResponse(objRealCreatorListRes: ObjRealCreatorListRes)
    fun getRealCreatorListFailureResponse(objRealCreatorListRes: ObjRealCreatorListRes)
}

