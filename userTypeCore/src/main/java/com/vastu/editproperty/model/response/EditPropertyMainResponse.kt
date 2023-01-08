package com.vastu.editproperty.model.response


import com.google.gson.annotations.SerializedName

data class EditPropertyMainResponse(
    @SerializedName("EditPropertyResponse")
    val editPropertyResponse: EditPropertyResponse
)