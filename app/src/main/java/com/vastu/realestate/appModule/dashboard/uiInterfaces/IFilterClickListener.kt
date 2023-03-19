package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.realestatecore.model.request.ObjFilterData

interface IFilterClickListener {
    fun setFilterView()
    fun applyFilters(objFilterData: ObjFilterData)
}