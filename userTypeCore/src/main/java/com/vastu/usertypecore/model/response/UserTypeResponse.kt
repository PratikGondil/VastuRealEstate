package com.vastu.usertypecore.model.response


import com.google.gson.annotations.SerializedName

data class UserTypeResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
)