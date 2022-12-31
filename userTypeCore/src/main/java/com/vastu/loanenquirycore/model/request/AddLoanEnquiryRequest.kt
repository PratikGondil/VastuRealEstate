package com.vastu.loanenquirycore.model.request


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddLoanEnquiryRequest(
    @SerializedName("first_name")val firstName: String?=null,
    @SerializedName("middle_name")val middleName: String?=null,
    @SerializedName("last_name")val lastName: String?=null,
    @SerializedName("mobile")val mobile: String?=null,
    @SerializedName("address")val address: String?=null,
    @SerializedName("occupation")val occupation: String?=null,
    @SerializedName("interested_in")val interestedIn: String?=null,
    @SerializedName("preferred_bank")val preferredBank: String?=null,
    @SerializedName("loan_amount")val loanAmount: String?=null,
    @SerializedName("loan_term_year")val loanTermYear: String?=null
): Serializable