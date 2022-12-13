package com.vastu.realestate.appModule.signUp.viewModel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.signUp.uiInterfaces.ISignUpViewListener
import com.vastu.realestate.registrationcore.callbacks.response.IResgisterResponseListener
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo
import com.vastu.realestate.registrationcore.model.response.ObjRegisterResponseMain
import com.vastu.realestate.registrationcore.repository.RegistrationRepository

class SignUpViewModel(application: Application) : AndroidViewModel(application),IResgisterResponseListener {
    var firstName = ObservableField("")
    var middleName = ObservableField("")
    var lastName = ObservableField("")
    var mobileNumber = ObservableField("")
    var mailId = ObservableField("")
    var city = ObservableField("")
    var subArea = ObservableField("")
    var cityList =MutableLiveData<String>()
    var subAreaList = MutableLiveData<String>()
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
        RegistrationRepository.callRegisterUserApi(objUserInfo,"register.php",this)
    }
    fun callCityListApi(){

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
}