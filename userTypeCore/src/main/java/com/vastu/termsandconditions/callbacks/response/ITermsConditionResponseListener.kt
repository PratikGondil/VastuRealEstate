package com.vastu.termsandconditions.callbacks.response

import com.vastu.termsandconditions.model.respone.TermsConditionMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface ITermsConditionResponseListener : NetworkFailedListener {
    fun onTermsConditionSuccess(termsConditionMainResponse: TermsConditionMainResponse)
    fun onTermsConditionFailure(termsConditionMainResponse: TermsConditionMainResponse)
}