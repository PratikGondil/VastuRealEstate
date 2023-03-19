package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.model.response.ObjGetFilterDataResponse

interface IFilterViewHandler {
    fun addFilterChip(text:String)
    fun removeChip(id: Int)
    fun applyFilters()
    fun clearFilter()
    fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain)
    fun onSubAreaClickListener(currentArea:ObjCityAreaData,isSelected:Boolean)
    fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain)

}