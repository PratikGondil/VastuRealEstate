package com.vastu.enquiry.loan.model.response


import com.google.gson.annotations.SerializedName

data class LoanData(
    @SerializedName("loan_enq_id")val loanId: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("middle_name")val middleName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("mobile")val mobile: String?,
    @SerializedName("address")val address: String?,
    @SerializedName("occupation")val occupation: String?,
    @SerializedName("interested_in")val interestedIn: String?,
    @SerializedName("preferred_bank")val preferredBank: String?,
    @SerializedName("loan_amount")val loanAmount: String?,
    @SerializedName("loan_term_year")val loanTermYear: String?,
    @SerializedName("created_at")val createdAt: String?,
    @SerializedName("emp_name") val assignee :String?=null,
    @SerializedName("status_name") val status :String?= null
):java.io.Serializable