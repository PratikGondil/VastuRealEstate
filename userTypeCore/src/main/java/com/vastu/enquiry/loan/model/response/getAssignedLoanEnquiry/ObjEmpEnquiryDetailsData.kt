package com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry

import com.google.gson.annotations.SerializedName

data class ObjEmpEnquiryDetailsData(
    @SerializedName("loan_enq_id"    ) var loanEnqId     : String? = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("mobile"         ) var mobile        : String? = null,
    @SerializedName("address"        ) var address       : String? = null,
    @SerializedName("occupation"     ) var occupation    : String? = null,
    @SerializedName("interested_in"  ) var interestedIn  : String? = null,
    @SerializedName("preferred_bank" ) var preferredBank : String? = null,
    @SerializedName("loan_amount"    ) var loanAmount    : String? = null,
    @SerializedName("loan_term_year" ) var loanTermYear  : String? = null,
    @SerializedName("status_name") var statusName : String? = null,
    @SerializedName("remark") var remark : String? = null
):java.io.Serializable
