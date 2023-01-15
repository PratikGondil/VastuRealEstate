package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain

interface IAddPropertyListener: INetworkFailListener{
    fun onClickUploadImage(image:Int)
    fun onClickAddProperty()
    fun onFailureEditProperty(editPropertyMainResponse: EditPropertyMainResponse)
    fun onSuccessEditProperty(editPropertyMainResponse: EditPropertyMainResponse)
    fun onSuccessAddProperty(addPropertyMainResponse: AddPropertyMainResponse)
    fun onFailureAddProperty(addPropertyMainResponse: AddPropertyMainResponse)
    fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain)
    fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain)
}