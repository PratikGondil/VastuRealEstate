package com.vastu.realCreator.creatorDetails.callback

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetCreatorDetailsResListener: NetworkFailedListener {
    fun getDetailsCreatorSuccessResponse(objDetailsCreatorRes: ObjDetailsCreatorRes)
    fun getDetailsCreatorFailureResponse(objDetailsCreatorRes: ObjDetailsCreatorRes )
}