package com.vastu.getimages.callbacks.request

import android.content.Context
import com.vastu.getimages.callbacks.response.IGetImagesResponseListener
import com.vastu.getimages.model.request.GetImageRequest

interface IGetImagesRequest {
    fun callGetImagesApi(context: Context, getImageRequest: GetImageRequest, urlEndPoint:String,iGetImagesResponseListener: IGetImagesResponseListener)
}