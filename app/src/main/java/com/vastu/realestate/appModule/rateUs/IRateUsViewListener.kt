package com.vastu.realestate.appModule.rateUs


interface IRateUsViewListener {
    fun onRateUsFail(objRateUsResponse: ObjRateUsResponse)
    fun launchDashboardScreen(rateUsDataResponseMain: RateUsDataResponseMain)
    fun onSubmitBtnClick()
}