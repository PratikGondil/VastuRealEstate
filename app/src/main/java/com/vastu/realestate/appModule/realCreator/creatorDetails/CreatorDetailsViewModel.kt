package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsResListener
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.creatorDetails.repository.DetailsCreatorRepository
import com.vastu.realCreator.rate_us.callback.IGetRatUsCreatorResListener
import com.vastu.realCreator.rate_us.model.ObjCreatorRateUsRes
import com.vastu.realCreator.rate_us.repository.CreatorRateUsRepository
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.utils.ApiUrlEndPoints

class CreatorDetailsViewModel(application: Application) : AndroidViewModel(application),IGetCreatorDetailsResListener,
    IGetRatUsCreatorResListener {
    lateinit var iCreatorDetailsListener: ICreatorDetailsListener
    lateinit var mContext:Application
    lateinit var realcreatorID:String
    lateinit var rate:String
    init {
        mContext=application
    }


    override fun getDetailsCreatorSuccessResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorDetailsListener.onSuccessGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun getDetailsCreatorFailureResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorDetailsListener.onFailureGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun getRealCreatorRateUsSuccessResponse(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes) {
        iCreatorDetailsListener.onSuccessGetRateUs(objcreatorRateUsRes)
    }

    override fun getRealCreatorRateUsFailureResponse(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes) {
       iCreatorDetailsListener.onFailureGetRateUs(objcreatorRateUsRes)
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
    fun onRateUsClick(){
        iCreatorDetailsListener.onRateUsClick()
    }

    fun callRateUsAPI(
        userID: String,
        rating: String,
        realCreatorID: String
    ) {
        CreatorRateUsRepository.callGetRateUs(mContext,
            userID,rating,realCreatorID, ApiUrlEndPoints.GET_REAL_CREATOR_RATE_US,this)

    }
    fun creatorDetailsAPICall(langauage:String,id:String){
        DetailsCreatorRepository.callGetCreatorDetailsList(mContext,id,langauage, ApiUrlEndPoints.GET_REAL_CREATOR_DETAILS,this)
    }

}