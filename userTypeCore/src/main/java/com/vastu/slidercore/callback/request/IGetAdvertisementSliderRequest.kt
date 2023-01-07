package com.vastu.slidercore.callback.request

import android.content.Context
import com.vastu.slidercore.callback.response.IGetAdvertisementResponseListener

interface IGetAdvertisementSliderRequest {
    fun callGetAdvertisementSlider(context: Context,urlEndPoint:String,iGetAdvertisementResponseListener: IGetAdvertisementResponseListener)
}