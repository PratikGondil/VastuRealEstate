package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsResListener
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.creatorDetails.repository.DetailsCreatorRepository
import com.vastu.realestate.utils.ApiUrlEndPoints

class CreatorDetailsViewModel(application: Application) : AndroidViewModel(application),IGetCreatorDetailsResListener {
    lateinit var iCreatorDetailsListener: ICreatorDetailsListener
    lateinit var mContext:Application
    init {
        mContext=application
    }


    override fun getDetailsCreatorSuccessResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorDetailsListener.onSuccessGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun getDetailsCreatorFailureResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorDetailsListener.onFailureGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun networkFailure() {

    }

     fun onWhatsAppClick() {
        iCreatorDetailsListener.onWhatsAppClick()
    }

     fun onShareClick() {
        iCreatorDetailsListener.onShareClick()
    }

     fun onEmailClick() {
       iCreatorDetailsListener.onEmailClick()
    }

     fun onCallClick() {
        iCreatorDetailsListener.onCallClick()
    }

}