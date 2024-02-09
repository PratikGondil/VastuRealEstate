package com.vastu.realestate.appModule.realCreator.creatorsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsResListener
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.creatorDetails.repository.DetailsCreatorRepository
import com.vastu.realCreator.realCreatorList.callback.IGetRealCreatorListResListener
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.realCreator.realCreatorList.repository.RealCreatorListRepository
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realCreator.realCreatorSearch.repository.CreatorListRepository
import com.vastu.realestate.appModule.realCreator.creatorDetails.ICreatorApiListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class CreatorListViewModel(application: Application) : AndroidViewModel(application),
    IGetRealCreatorListResListener, IGetCreatorDetailsResListener {
    lateinit var iCreatorApiListener: ICreatorApiListener
    lateinit var iCreatorListListener:ICreatorListListener
    var mContext :Application
    init {
        mContext = application
    }
    fun getRealCreatorList(profileID:String, endpoint:String, language:String,taluka:String,subarea:String,userID:String){
        RealCreatorListRepository.callGetRealCreatorList(mContext,profileID,language,taluka,subarea,userID,endpoint,this)
    }
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        iCreatorListListener.searchFilter(s.toString())
    }


    override fun getRealCreatorListSuccessResponse(objRealCreatorListRes: ObjRealCreatorListRes) {
        iCreatorListListener.onSuccessGetRealCreatorList(objRealCreatorListRes)
    }

    override fun getRealCreatorListFailureResponse(objRealCreatorListRes: ObjRealCreatorListRes) {
        iCreatorListListener.onFailureGetRealCreatorList(objRealCreatorListRes)
    }

    override fun getDetailsCreatorSuccessResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorApiListener.onSuccessGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun getDetailsCreatorFailureResponse(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        iCreatorApiListener.onFailureGetRealCreatorList(objDetailsCreatorRes)
    }

    override fun networkFailure() {
       ///
    }


    fun apiCallRepo(langauage:String,id:String){
        DetailsCreatorRepository.callGetCreatorDetailsList(mContext,id,langauage, ApiUrlEndPoints.GET_REAL_CREATOR_DETAILS,this)
    }


}