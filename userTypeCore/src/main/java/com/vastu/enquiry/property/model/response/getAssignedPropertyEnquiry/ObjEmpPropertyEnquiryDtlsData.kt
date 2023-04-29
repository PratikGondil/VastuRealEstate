package com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry

import com.google.gson.annotations.SerializedName

data class ObjEmpPropertyEnquiryDtlsData(
    @SerializedName("pro_enq_id") var proEnqId : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("mobile") var mobile : String? = null,
    @SerializedName("address") var address : String? = null,
    @SerializedName("occupation") var occupation: String? = null,
    @SerializedName("interested_in") var interestedIn : String? = null,
    @SerializedName("ownership") var ownership: String? = null,
    @SerializedName("area") var area : String? = null,
    @SerializedName("budget") var budget : String? = null,
    @SerializedName("status_name") var status_name:String?=null,
    @SerializedName("remark") var remark:String?=null
):java.io.Serializable
