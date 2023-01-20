package com.vastu.enquiry.statusUpdate.enquiryStatus.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjEnquiryStatusData (
    @SerializedName("status_id") var statusId : String? = null,
    @SerializedName("status_name") var statusName : String? = null
        ): Serializable {
    override fun toString(): String {
        return "$statusName"
    }
}
