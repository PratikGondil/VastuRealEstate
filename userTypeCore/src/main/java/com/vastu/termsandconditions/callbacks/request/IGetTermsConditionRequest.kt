package com.vastu.termsandconditions.callbacks.request

import android.content.Context
import com.vastu.termsandconditions.callbacks.response.ITermsConditionResponseListener

interface IGetTermsConditionRequest {
    fun callTermsCondition(context: Context, urlEndPoint:String,iTermsConditionResponseListener: ITermsConditionResponseListener)
}