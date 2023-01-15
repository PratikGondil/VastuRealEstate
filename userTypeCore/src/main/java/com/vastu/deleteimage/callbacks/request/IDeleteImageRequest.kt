package com.vastu.deleteimage.callbacks.request

import android.content.Context
import com.vastu.deleteimage.callbacks.response.IDeleteImageResponseListener
import com.vastu.deleteimage.model.request.DeleteImageRequest
import com.vastu.getimages.model.request.GetImageRequest

interface IDeleteImageRequest {
    fun callDeleteImage(context: Context, deleteImageRequest: DeleteImageRequest, urlEndPoint:String,iDeleteImageResponseListener: IDeleteImageResponseListener)
}