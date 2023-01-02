package com.vastu.realestate.appModule.signUp.viewModel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.signUp.uiInterfaces.ISignUpViewListener
import com.vastu.realestate.registrationcore.callbacks.response.IResgisterResponseListener
import com.vastu.realestate.registrationcore.callbacks.response.ISubAreaResponseListener
import com.vastu.realestate.registrationcore.callbacks.response.ITalukaResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.registrationcore.repository.CityListRequestRepository
import com.vastu.realestate.registrationcore.repository.RegistrationRepository
import com.vastu.realestate.registrationcore.repository.SubAreaRequestRepository
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_CITIES
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_SUB_CITY
import com.vastu.realestate.utils.ApiUrlEndPoints.REGISTER

class SignUpViewModel(application: Application) : AndroidViewModel(application),IResgisterResponseListener,ITalukaResponseListener ,ISubAreaResponseListener{
    var firstName = ObservableField("")
    var middleName = ObservableField("")
    var lastName = ObservableField("")
    var mobileNumber = ObservableField("")
    var mailId = ObservableField("")
    var city = MutableLiveData<ObjTalukaDataList>()
    var subArea = ObservableField<ObjCityAreaData>()
    var cityList =MutableLiveData<ArrayList<ObjTalukaDataList>>()
    var subAreaList = MutableLiveData<ArrayList<ObjCityAreaData>>()
    var isBtnEnable =ObservableField(false)
    var mContext :Application
    init {
        mContext =application
    }
    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))
    lateinit var iSignUpViewListener : ISignUpViewListener
    fun onSubmitBtnClick(){
        iSignUpViewListener.registerUser()
    }

    fun callRegistrationApi(objUserInfo: ObjUserInfo){
        RegistrationRepository.callRegisterUserApi(mContext,objUserInfo, REGISTER,this)
    }
    fun callCityListApi(){
        CityListRequestRepository.callCityListApi(mContext,GET_CITIES,this)
    }

    fun callSubAreaList(talukaId: ObjSubAreaReq){
        SubAreaRequestRepository.callSubAreaListApi(mContext,talukaId,GET_SUB_CITY,this)
    }
    override fun onGetSuccessResponse(objRegisterResponseMain: ObjRegisterResponseMain) {
//        iSignUpViewListener.launchOtpScreen(objRegisterResponseMain.objRegisterDlts)
        iSignUpViewListener.goToLogin()
    }

    override fun onGetFailureResponse(objRegisterResponseMain: ObjRegisterResponseMain) {
        iSignUpViewListener.onRegistrationFail(objRegisterResponseMain)
    }

    override fun onAlreadyExistUser(objRegisterResponseMain: ObjRegisterResponseMain) {
        iSignUpViewListener.onRegistrationFail(objRegisterResponseMain)
        iSignUpViewListener.goToLogin()
    }

    override fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        cityList.value = objTalukaResponseMain.objTalukaDetailResponse.objTalukaDataList as ArrayList<ObjTalukaDataList>
    }

    override fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
       iSignUpViewListener.onCityListApiFailure(objTalukaResponseMain)
    }

    override fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain) {
        subAreaList.value = response.objGetCityAreaDetailsResponse.objCityAreaData as ArrayList<ObjCityAreaData>
    }

    override fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain) {
        iSignUpViewListener.onSubAreaListApiFailure(responseMain)
    }

    override fun networkFailure() {
        iSignUpViewListener.onUserNotConnected()
    }
}