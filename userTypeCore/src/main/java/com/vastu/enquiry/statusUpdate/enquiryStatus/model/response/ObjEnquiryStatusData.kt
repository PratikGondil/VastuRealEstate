package com.vastu.enquiry.statusUpdate.enquiryStatus.model.response

import com.google.gson.annotations.SerializedName

data class ObjEnquiryStatusData (
    @SerializedName("status_id") var statusId : String? = null,
    @SerializedName("status_name") var statusName : String? = null
        )
