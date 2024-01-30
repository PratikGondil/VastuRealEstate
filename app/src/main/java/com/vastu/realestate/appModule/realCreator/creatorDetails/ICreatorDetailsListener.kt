package com.vastu.realestate.appModule.realCreator.creatorDetails

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.rate_us.model.ObjCreatorRateUsRes

interface ICreatorDetailsListener:ICreatorApiListener {
    fun onWhatsAppClick()
    fun onShareClick()
    fun onEmailClick()
    fun onCallClick()
    fun onSuccessGetRateUs(objcreatorRateUsRes:ObjCreatorRateUsRes.CreatorRateUsRes)
    fun onFailureGetRateUs(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes)
    fun onRateUsClick()

}