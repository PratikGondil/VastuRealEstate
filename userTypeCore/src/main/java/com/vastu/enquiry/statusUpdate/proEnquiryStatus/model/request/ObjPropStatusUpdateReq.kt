package com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request

import com.google.gson.annotations.SerializedName

data class ObjPropStatusUpdateReq(
    @SerializedName("emp_id") var emp_id : String? = null,
    @SerializedName("pro_enq_id") var pro_enq_id : String? = null,
    @SerializedName("status") var status :String? = null
)
