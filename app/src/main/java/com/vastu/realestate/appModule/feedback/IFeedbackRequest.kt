package com.vastu.realestate.appModule.feedback

import android.content.Context

interface IFeedbackRequest {
    fun callFeedbackApi(context:Context, userId:String, feedbackSelect:String, feedbackText:String, urlEndPoint:String, iFeedbackResponseListener: IFeedbackResponseListener)
}