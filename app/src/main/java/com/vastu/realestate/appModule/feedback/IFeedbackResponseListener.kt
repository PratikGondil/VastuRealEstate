package com.vastu.realestate.appModule.feedback

import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IFeedbackResponseListener : NetworkFailedListener {
    fun onGetSuccessResponse(response: FeedbackDataResponseMain)
    fun onGetFailureResponse(response: FeedbackDataResponseMain)
}