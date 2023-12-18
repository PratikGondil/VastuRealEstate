package com.vastu.realestate.appModule.realCreator.creatorsList

import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface ICreatorListListener:INetworkFailListener {
    fun onSuccessGetRealCreatorList(objRealCreatorListRes: ObjRealCreatorListRes)
    fun onFailureGetRealCreatorList(objRealCreatorListRes: ObjRealCreatorListRes)
    fun searchFilter(searchTxt:String)
}