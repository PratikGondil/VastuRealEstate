package com.vastu.slidercore.callback.request

import com.vastu.slidercore.callback.response.IGetAdvertisementResponseListener

interface IGetAdvertisementSliderRequest {
    fun callGetAdvertisementSlider(urlEndPoint:String,iGetAdvertisementResponseListener: IGetAdvertisementResponseListener)
}