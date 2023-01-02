package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.registrationcore.model.ObjManageFilterVisibility

class FilterViewModel(application: Application):AndroidViewModel (application){
    var mContext:Application

init {
    mContext =application
}

    var title = ObservableField(mContext.resources.getString(R.string.choose_a_range_below))
    var range = ObservableField(ContextCompat.getDrawable(mContext,R.drawable.budget_range_icon))
    var lowerLimit = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_lower_limit)))
    var upperLimit = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_upper_limit)))
    var budgetSliderLowerLimit = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_lower_limit)))
    var lowerLimitForPerSq = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_lower_limit)))
    var upperLimitForPerSq = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.SqFt_upper_limit)))
    var lowerLimitForBuildupArea = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_lower_limit)))
    var upperLimitForBuildupArea = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.buildup_area_upper_limit)))
    var isHousesSelected = ObservableField(false)
    var isApartmentSelected = ObservableField(false)
    var isBuilerFloorSelected = ObservableField(false)
    var isFarmHousesSelected = ObservableField(false)
    var isVisibleBudgetLayout = ObservableField(true)
    var isVisiblePropertyLayout = ObservableField(false)
    var isVisiblePricePerSqFtLayout = ObservableField(false)
    var isVisibleByBedroomsLayout = ObservableField(false)
    var isVisibleByBathroomsLayout = ObservableField(false)
    var isVisibleByFurnishingLayout = ObservableField(false)
    var isVisibleConstructionStsLayout = ObservableField(false)
    var isVisibleListedLayout = ObservableField(false)
    var isVisibleBuildupAreaLayout = ObservableField(false)
    var isVisibleChangeSortLayout = ObservableField(false)
    lateinit var iFilterViewHandler : IFilterViewHandler
    fun setSelectedView(objManageFilterVisibility: ObjManageFilterVisibility){
        title.set(objManageFilterVisibility.title)
        isVisibleBudgetLayout.set(objManageFilterVisibility.isVisibleBudgetLayout)
        isVisiblePropertyLayout.set(objManageFilterVisibility.isVisiblePropertyLayout)
        isVisiblePricePerSqFtLayout.set(objManageFilterVisibility.isVisiblePricePerSqFtLayout)
        isVisibleByBedroomsLayout.set(objManageFilterVisibility.isVisibleByBedroomsLayout)
        isVisibleByBathroomsLayout.set(objManageFilterVisibility.isVisibleByBathroomsLayout)
        isVisibleByFurnishingLayout.set(objManageFilterVisibility.isVisibleByFurnishingLayout)
        isVisibleConstructionStsLayout.set(objManageFilterVisibility.isVisibleConstructionStsLayout)
        isVisibleListedLayout.set(objManageFilterVisibility.isVisibleListedLayout)
        isVisibleBuildupAreaLayout.set(objManageFilterVisibility.isVisibleBuildupAreaLayout)
        isVisibleChangeSortLayout.set(objManageFilterVisibility.isVisibleChangeSortLayout)
    }

    fun addChip(text:String){
        iFilterViewHandler.addFilterChip(text)
    }
    fun removeChip(id: Int) {
        iFilterViewHandler.removeChip(id)
    }
}