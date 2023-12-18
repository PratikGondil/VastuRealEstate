package com.vastu.realestate.appModule.realCreator.creatorsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realCreator.realCreatorList.callback.IGetRealCreatorListResListener
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.realCreator.realCreatorList.repository.RealCreatorListRepository
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realCreator.realCreatorSearch.repository.CreatorListRepository

class CreatorListViewModel(application: Application) : AndroidViewModel(application),
    IGetRealCreatorListResListener {
    lateinit var iCreatorListListener:ICreatorListListener
    var mContext :Application
    init {
        mContext = application
    }
    fun getRealCreatorList(userId:String, endpoint:String, language:String){
        RealCreatorListRepository.callGetRealCreatorList(mContext,"2",language,endpoint,this)
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

    override fun networkFailure() {
       ///
    }




}