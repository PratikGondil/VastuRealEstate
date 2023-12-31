package com.vastu.realestate.appModule.signUp.uiInterfaces

import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterDlts
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.termsandconditions.model.respone.TermsConditionMainResponse

interface ISignUpViewListener : INetworkFailListener {
    fun registerUser()
    fun launchOtpScreen(objRegisterDlts: ObjRegisterDlts)
    fun goToLogin()
    fun onRegistrationFail(objRegisterResponseMain: ObjRegisterResponseMain)
    fun onCityListApiFailure(objTalukaResponseMain:ObjTalukaResponseMain)
    fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain)
    fun onSuccessTermsCondition(termsConditionMainResponse: TermsConditionMainResponse)
    fun onFailureTermsCondition(termsConditionMainResponse: TermsConditionMainResponse)
    fun onSuccessTerms()
    fun onFailureTerms()
}