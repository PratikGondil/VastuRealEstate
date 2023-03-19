package com.vastu.realestatecore.model.filter

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjManageFilterVisibility(
    @SerializedName("isVisibleCityLayout") var isVisibleCityLayout:Boolean = true,
    @SerializedName("isVisibleSubAreaLayout") var isVisibleSubAreaLayout:Boolean = false,
    @SerializedName("isVisibleBudgetLayout") var isVisibleBudgetLayout:Boolean = false,
    @SerializedName("isVisiblePropertyLayout") var isVisiblePropertyLayout:Boolean = false,
    @SerializedName("isVisiblePricePerSqFtLayout") var isVisiblePricePerSqFtLayout:Boolean = false,
    @SerializedName("isVisibleByBedroomsLayout") var isVisibleByBedroomsLayout:Boolean = false,
    @SerializedName("isVisibleByBathroomsLayout") var isVisibleByBathroomsLayout:Boolean = false,
    @SerializedName("isVisibleByFurnishingLayout") var isVisibleByFurnishingLayout:Boolean = false,
    @SerializedName("isVisibleConstructionStsLayout") var isVisibleConstructionStsLayout:Boolean = false,
    @SerializedName("isVisibleListedLayout") var isVisibleListedLayout:Boolean = false,
    @SerializedName("isVisibleBuildupAreaLayout") var isVisibleBuildupAreaLayout:Boolean = false,
    @SerializedName("isVisibleChangeSortLayout") var isVisibleChangeSortLayout:Boolean = false,
    @SerializedName("title") var title :String = ""

):Serializable
