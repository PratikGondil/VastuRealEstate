package com.vastu.realestate.appModule.realCreator.creatorDetails

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes

interface ICreatorDetailsListener:ICreatorApiListener {
    fun onWhatsAppClick()
    fun onShareClick()
    fun onEmailClick()
    fun onCallClick()

}