package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain

interface IFilterViewHandler {
    fun addFilterChip(text:String)
    fun removeChip(id: Int)
    fun applyFilters()
    fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain)
    fun onSubAreaClickListener(currentArea:ObjCityAreaData,isSelected:Boolean)
}