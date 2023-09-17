package com.vastu.realestate.appModule.rateUs

import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IRateUsResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: RateUsDataResponseMain)
    fun onGetFailureResponse(response: RateUsDataResponseMain)
}