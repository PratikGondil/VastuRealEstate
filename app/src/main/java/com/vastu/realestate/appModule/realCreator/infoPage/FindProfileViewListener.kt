package com.vastu.realestate.appModule.realCreator.infoPage

import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListReq
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain

interface FindProfileViewListener: INetworkFailListener {
    fun onSubmitBtnClick()
    fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain)
    fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain)
    fun onCreatorPrfileListApiFailure(objCreatorListRes: ObjCreatorListRes)
}