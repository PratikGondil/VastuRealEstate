package com.vastu.realestate.appModule.realCreator.creatorDetails

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes

interface ICreatorDetailsListener {
    fun onWhatsAppClick()
    fun onShareClick()
    fun onEmailClick()
    fun onCallClick()
    fun onSuccessGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes)
    fun onFailureGetRealCreatorList(objDetailsCreatorRes:ObjDetailsCreatorRes)
}