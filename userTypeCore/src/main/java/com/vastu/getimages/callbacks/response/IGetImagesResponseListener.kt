package com.vastu.getimages.callbacks.response

import com.vastu.getimages.model.response.GetImageMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetImagesResponseListener:NetworkFailedListener {
    fun onGetImagesSuccess(getImageMainResponse: GetImageMainResponse)
    fun onGetImageFailure(getImageMainResponse: GetImageMainResponse)
}