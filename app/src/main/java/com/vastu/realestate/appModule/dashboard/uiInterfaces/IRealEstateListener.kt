package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.model.response.ObjGetFilterDataResponse
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain

interface IRealEstateListener:INetworkFailListener {
    fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain)
    fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain)
    fun onFilterPropertyListSuccess(objGetFilterDataResponse: ObjGetFilterDataResponse)
    fun onFilterPropertyListFailure(objFilterDataResponseMain: ObjFilterDataResponseMain)
}