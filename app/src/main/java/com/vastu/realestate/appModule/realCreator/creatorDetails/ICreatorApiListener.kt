package com.vastu.realestate.appModule.realCreator.creatorDetails

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes

interface ICreatorApiListener {
    fun onSuccessGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes)
    fun onFailureGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes)



}