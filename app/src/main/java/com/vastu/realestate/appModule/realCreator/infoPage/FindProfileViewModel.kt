package com.vastu.realestate.appModule.realCreator.infoPage

import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.networkService.util.Constants
import com.vastu.realCreator.creatorDetails.callback.IGetCreatorDetailsResListener
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.creatorDetails.model.SingalRealCreatorDatum
import com.vastu.realCreator.realCreatorList.model.RealCreatorDatum
import com.vastu.realCreator.realCreatorSearch.callback.IGetCreatorListResListener
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realCreator.realCreatorSearch.model.ProfileDaum
import com.vastu.realCreator.realCreatorSearch.repository.CreatorListRepository
import com.vastu.realestate.R
import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.registrationcore.repository.CityListRequestRepository
import com.vastu.realestate.registrationcore.repository.SubAreaRequestRepository
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.PreferenceManger

class FindProfileViewModel(application: Application) :AndroidViewModel(application),
    ITalukaResponseListener, ISubAreaResponseListener, IGetCreatorListResListener {
    lateinit var  iGetCreatorDetailsResListener: IGetCreatorDetailsResListener
    lateinit var findProfileViewListener: FindProfileViewListener
    lateinit var mContext: Application
    var selectedQuery: ObservableField<String> = ObservableField<String>()


    init {
        mContext = application
        selectedQuery.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("***", "text :${selectedQuery.get()}")
            }
        })
    }

    var city = MutableLiveData<ObjTalukaDataList>()
    var subArea = ObservableField<ObjCityAreaData>()
    var profile = MutableLiveData<ProfileDaum>()
    var cityList = MutableLiveData<ArrayList<ObjTalukaDataList>>()
    var subAreaList = MutableLiveData<ArrayList<ObjCityAreaData>>()
    var profileList = MutableLiveData<List<ProfileDaum>>()
    var isBtnEnable =ObservableField(false)
    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))


    var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
    val allQuery: ObservableField<List<String>> =
        ObservableField<List<String>>(
            listOf(
                mContext.resources.getString(R.string.select),
                mContext.resources.getString(R.string.app_crash),
                mContext.resources.getString(R.string.search_issue),
                mContext.resources.getString(R.string.asking_question),
                mContext.resources.getString(R.string.improvement),
                mContext.resources.getString(R.string.other)
            )
        )


    val allQueryMarathi: ObservableField<List<String>> =
        ObservableField<List<String>>(
            listOf(
                "निवडा",
                "अॅप क्रॅश/स्लो डाउन शोध समस्या",
                "शोध समस्या",
                "एक प्रश्न सूचना विचारणे",
                "सुधारणा",
                "इतर"
            )
        )


    fun callCityListApi(language: String?) {
        CityListRequestRepository.callCityListApi(mContext,
            ApiUrlEndPoints.GET_CITIES,language!!,this)
    }
    fun callProfileListApi(language: String?)
    {
        if (language != null) {
            CreatorListRepository.callGetCreatorList(mContext,language,ApiUrlEndPoints.GET_REAL_CREATOR_PROFILE, this)
        }
    }
    fun callSubAreaList(talukaId: ObjSubAreaReq){
        SubAreaRequestRepository.callSubAreaListApi(mContext,talukaId,
            ApiUrlEndPoints.GET_SUB_CITY,this)
    }




    fun submitClick(){
        findProfileViewListener.onSubmitBtnClick()
    }







    override fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        cityList.value = objTalukaResponseMain.objTalukaDetailResponse.objTalukaDataList as ArrayList<ObjTalukaDataList>
    }

    override fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        findProfileViewListener.onCityListApiFailure(objTalukaResponseMain)
    }

    override fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain) {
        subAreaList.value = response.objGetCityAreaDetailsResponse.objCityAreaData as ArrayList<ObjCityAreaData>
    }

    override fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain) {
        findProfileViewListener.onSubAreaListApiFailure(responseMain)
    }



    override fun getCreatorListSuccessResponse(objCreatorListRes: ObjCreatorListRes) {
            profileList.value=objCreatorListRes.getProfileDetailsResponse.profileData

    }

    override fun getCreatorListFailureResponse(objCreatorListRes: ObjCreatorListRes) {
        findProfileViewListener.onCreatorPrfileListApiFailure(objCreatorListRes)
    }


    override fun networkFailure() {
        findProfileViewListener.onUserNotConnected()
    }

}