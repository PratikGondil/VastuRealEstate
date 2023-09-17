package com.vastu.realestate.appModule.feedback

interface IFeedbackViewListener {
    fun onFeedbackFail(objFeedbackResponse: ObjFeedbackResponse)
    fun launchDashboardScreen(feedbackDataResponseMain: FeedbackDataResponseMain)
    fun onSubmitBtnClick()
}