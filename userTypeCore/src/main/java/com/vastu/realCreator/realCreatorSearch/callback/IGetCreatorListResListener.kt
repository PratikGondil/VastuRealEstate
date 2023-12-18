package com.vastu.realCreator.realCreatorSearch.callback

import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetCreatorListResListener:NetworkFailedListener {
    fun getCreatorListSuccessResponse(objCreatorListRes: ObjCreatorListRes)
    fun getCreatorListFailureResponse(objCreatorListRes: ObjCreatorListRes)
}
