package com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlanDuration(
    @SerializedName("selectedRadio")
    var selectedRadio:Boolean?=false,
    @SerializedName("months")
    var months: String?="",
    @SerializedName("amtIncGst") val amtIncGst: String?="",
    @SerializedName("amtMonthly")
     val amtMonthly: String?=""
):Serializable


