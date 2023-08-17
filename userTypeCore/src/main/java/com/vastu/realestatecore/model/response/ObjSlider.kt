package com.vastu.realestatecore.model.response

import com.google.gson.annotations.SerializedName

data class ObjSlider(
    @SerializedName("id")
    var id       : String?  = "",
    @SerializedName("slider")
    var slider   : String?  = "",
    @SerializedName("link")
    var link     : String?  = "",
    @SerializedName("position")
    var position : String?  = "",
    @SerializedName("video")
    var video    : Boolean? = false

)
