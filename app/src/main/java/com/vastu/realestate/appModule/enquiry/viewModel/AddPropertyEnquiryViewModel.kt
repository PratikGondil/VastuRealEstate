package com.vastu.realestate.appModule.enquiry.viewModel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.loanenquirycore.callbacks.response.*
import com.vastu.loanenquirycore.model.request.AddPropertyEnquiryRequest
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.property.InterestedInData
import com.vastu.loanenquirycore.model.response.interest.property.PropertyInterestMainResponse
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.loanenquirycore.model.response.ownership.OwnershipData
import com.vastu.loanenquirycore.model.response.ownership.OwnershipMainResponse
import com.vastu.loanenquirycore.repository.*
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.uiinterfaces.IAddPropertyEnquiryListener
import com.vastu.realestate.utils.ApiUrlEndPoints.ADD_PROPERTY_ENQUIRY
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_OCCUPATION
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_OWNERSHIP
import com.vastu.realestate.utils.ApiUrlEndPoints.PROPERTY_INTERESTED_IN

class AddPropertyEnquiryViewModel(application: Application) :AndroidViewModel(application),
    IGetOccupationResponseListener,IGetPropertyInterestResponseListener, IGetOwnershipResponseListener,
    IAddPropertyEnquiryResponseListener{

    var firstName = ObservableField("")
    var middleName = ObservableField("")
    var lastName = ObservableField("")
    var mobileNumber = ObservableField("")
    var address = ObservableField("")

    var occupationName = MutableLiveData<OccupationData>()
    var occupationList = MutableLiveData<ArrayList<OccupationData>>()

    var propertyName = MutableLiveData<InterestedInData>()
    var propertyInterestedIn = MutableLiveData<ArrayList<InterestedInData>>()

    var ownershipName = MutableLiveData<OwnershipData>()
    var ownershipList = MutableLiveData<ArrayList<OwnershipData>>()

    var area = ObservableField("")
    var budget = ObservableField("")

    var isBtnEnable = ObservableField(false)

    private var mContext :Application
    init {
        mContext =application
    }

    var fName = ObservableField(mContext.getString(R.string.required))
    var mName = ObservableField(mContext.getString(R.string.required))
    var lName = ObservableField(mContext.getString(R.string.required))
    var mobile = ObservableField(mContext.getString(R.string.required))
    var add = ObservableField(mContext.getString(R.string.required))
    var occ = ObservableField(mContext.getString(R.string.required))
    var property = ObservableField(mContext.getString(R.string.required))
    var ownership = ObservableField(mContext.getString(R.string.required))
    var era = ObservableField(mContext.getString(R.string.required))
    var bud = ObservableField(mContext.getString(R.string.required))

    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))
    lateinit var iAddPropertyEnquiryListener: IAddPropertyEnquiryListener

     fun callGetOccupation(){
        OccupationRepository.callGetOccupation(mContext,GET_OCCUPATION,this)
    }
    fun callGetPropertyInterestedIn(){
        PropertyInterestRepository.callGetPropertyInterest(mContext,PROPERTY_INTERESTED_IN,this )
    }
    fun callGetOwnerShip(){
        OwnershipRepository.callGetOwnership(mContext,GET_OWNERSHIP,this)
    }
    fun callAddPropertyEnquiry(addPropertyRequest: AddPropertyEnquiryRequest){
        AddPropertyEnquiryRepository.callAddPropertyEnquiry(mContext,ADD_PROPERTY_ENQUIRY,addPropertyRequest,this)
    }
    fun onSubmitPropertyEnquiry(){
        iAddPropertyEnquiryListener.onClickAddPropertyEnquiry()
    }

    override fun getOccupationSuccessResponse(occupationMainResponse: OccupationMainResponse) {
        occupationList.value = occupationMainResponse.getoccupationDetailsResponse.occupationData as ArrayList<OccupationData>
    }

    override fun getOccupationFailureResponse(occupationMainResponse: OccupationMainResponse) {
        iAddPropertyEnquiryListener.onOccupationListFailure(occupationMainResponse)
    }

    override fun getOwnershipSuccessResponse(ownershipMainResponse: OwnershipMainResponse) {
            ownershipList.value = ownershipMainResponse.getownershipDetailsResponse.ownershipData as ArrayList<OwnershipData>
    }

    override fun getOwnershipFailureResponse(ownershipMainResponse: OwnershipMainResponse) {
        iAddPropertyEnquiryListener.onOwnershipListFailure(ownershipMainResponse)
    }

    override fun getPropertyInterestSuccessResponse(propertyInterestMainResponse: PropertyInterestMainResponse) {
            propertyInterestedIn.value = propertyInterestMainResponse.getInterestedInDetailsResponse.interestedInData as ArrayList<InterestedInData>
    }

    override fun getPropertyInterestFailureResponse(propertyInterestMainResponse: PropertyInterestMainResponse) {
        iAddPropertyEnquiryListener.onPropertyInterestedInListFailure(propertyInterestMainResponse)
    }

    override fun onAddPropertyEnquirySuccessResponse(enquiryMainResponse: EnquiryMainResponse) {
        iAddPropertyEnquiryListener.onGotoDashboard(enquiryMainResponse)
    }

    override fun onAddPropertyEnquiryFailureResponse(enquiryMainResponse: EnquiryMainResponse) {
       iAddPropertyEnquiryListener.onAddPropertyEnquiryFailure(enquiryMainResponse)
    }

    override fun networkFailure() {
        iAddPropertyEnquiryListener.onUserNotConnected()
    }

}