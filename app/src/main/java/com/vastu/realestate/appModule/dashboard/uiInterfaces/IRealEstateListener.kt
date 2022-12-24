package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain

interface IRealEstateListener {
    fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain)
    fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain)
}