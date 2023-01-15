package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.addproperty.callback.response.IAddPropertyResponseListener
import com.vastu.addproperty.model.request.AddPropertyRequest
import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.addproperty.repository.AddPropertyRepository
import com.vastu.deleteimage.callbacks.response.IDeleteImageResponseListener
import com.vastu.deleteimage.model.request.DeleteImageRequest
import com.vastu.deleteimage.model.response.DeleteImageMainResponse
import com.vastu.deleteimage.repository.DeleteImageRepository
import com.vastu.editproperty.callbacks.response.IEditPropertyResponseListener
import com.vastu.editproperty.model.request.EditPropertyRequest
import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.editproperty.repository.EditPropertyRepository
import com.vastu.getimages.callbacks.response.IGetImagesResponseListener
import com.vastu.getimages.model.request.GetImageRequest
import com.vastu.getimages.model.response.GetImageMainResponse
import com.vastu.getimages.repository.GetImageRepository
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.propertycore.repository.PropertyDetailsRepository
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAddPropertyListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IGetImagesListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
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
import com.vastu.realestate.utils.ApiUrlEndPoints.EDIT_PROPERTY

class AddPropertyViewModel(application: Application):AndroidViewModel(application),
    ITalukaResponseListener, ISubAreaResponseListener,IAddPropertyResponseListener,IEditPropertyResponseListener,
    IGetPropertyDetailsResponseListener,IGetImagesResponseListener,IDeleteImageResponseListener{

    var city = MutableLiveData<ObjTalukaDataList>()
    var subArea =  MutableLiveData<ObjCityAreaData>()
    var cityList = MutableLiveData<ArrayList<ObjTalukaDataList>>()
    var subAreaList = MutableLiveData<ArrayList<ObjCityAreaData>>()

    var propertyTitle = ObservableField("")
    var propertyType = ObservableField("")
    var sellType  = ObservableField("")
    var state = ObservableField("")
    var price = ObservableField("")
    var propertyAddress  = ObservableField("")
    var bookingAmount = ObservableField("")
    var garage = ObservableField("")
    var swimmingPool = ObservableField("")
    var balcony = ObservableField("")
    var floors  = ObservableField("")
    var areaSqFt = ObservableField("")
    var bedroom = ObservableField("")
    var bathroom = ObservableField("")
    var kitchen = ObservableField("")
    var availability  = ObservableField("")
    var buildYear = ObservableField("")
    var description = ObservableField("")
    var highlights = ObservableField("")
    var thumbnailImage = ObservableField("")
    var brochurePdf = ObservableField("")
    var amenties  = ObservableField("")

    private var mContext: Application
    init {
        mContext = application
    }
    lateinit var iAddPropertyListener: IAddPropertyListener
    lateinit var iPropertyDetailsListener:IPropertyDetailsListener
    lateinit var iGetImagesListener:IGetImagesListener

    var isBtnEnable = ObservableField(false)
    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))

    var propertyTitleValid = ObservableField(mContext.getString(R.string.required))
    var propertyTypeValid = ObservableField(mContext.getString(R.string.required))
    var sellTypeValid = ObservableField(mContext.getString(R.string.required))
    var stateValid = ObservableField(mContext.getString(R.string.required))
    var cityValid = ObservableField(mContext.getString(R.string.required))
    var subAreaValid = ObservableField(mContext.getString(R.string.required))
    var priceValid = ObservableField(mContext.getString(R.string.required))
    var propertyAddressValid = ObservableField(mContext.getString(R.string.required))
    var bookingAmountValid = ObservableField(mContext.getString(R.string.required))
    var areaSqFtValid = ObservableField(mContext.getString(R.string.required))
    var bedroomValid = ObservableField(mContext.getString(R.string.required))
    var bathroomValid = ObservableField(mContext.getString(R.string.required))
    var kitchenValid = ObservableField(mContext.getString(R.string.required))
    var availabilityValid = ObservableField(mContext.getString(R.string.required))
    var buildYearValid = ObservableField(mContext.getString(R.string.required))
    var descriptionValid = ObservableField(mContext.getString(R.string.required))
    var highlightsValid = ObservableField(mContext.getString(R.string.required))

    fun onClickThumbnail(){
        iAddPropertyListener.onClickUploadImage(0)
    }
    fun onClickImage1(){
         iAddPropertyListener.onClickUploadImage(1)
    }
    fun onClickImage2(){
        iAddPropertyListener.onClickUploadImage(2)
    }
    fun onClickImage3(){
        iAddPropertyListener.onClickUploadImage(3)
    }
    fun onClickImage4(){
        iAddPropertyListener.onClickUploadImage(4)
    }
    fun onClickImage5(){
        iAddPropertyListener.onClickUploadImage(5)
    }
    fun onClickBrochurePdf(){
        iAddPropertyListener.onClickUploadImage(6)
    }

    fun onSubmitAddProperty(){
        iAddPropertyListener.onClickAddProperty()
    }
    fun callAddPropertyApi(addPropertyRequest: AddPropertyRequest){
            AddPropertyRepository.callAddProperty(
                mContext,
                ApiUrlEndPoints.ADD_PROPERTY,
                addPropertyRequest,
                this
            )
    }
    fun callEditPropertyApi(editPropertyRequest: EditPropertyRequest){
        EditPropertyRepository.callEditProperty(mContext,EDIT_PROPERTY,editPropertyRequest,this)
    }
    fun getPropertyImages(getImageRequest: GetImageRequest){
        GetImageRepository.callGetImagesApi(mContext,getImageRequest,ApiUrlEndPoints.GET_IMAGES,this)
    }
    fun deleteImage(deleteImageRequest: DeleteImageRequest){
        DeleteImageRepository.callDeleteImage(mContext,deleteImageRequest,ApiUrlEndPoints.DELETE_IMAGES,this
        )
    }
    fun callCityListApi(){
        CityListRequestRepository.callCityListApi(mContext, ApiUrlEndPoints.GET_CITIES,this)
    }

    fun callSubAreaList(talukaId: ObjSubAreaReq){
        SubAreaRequestRepository.callSubAreaListApi(mContext,talukaId,
            ApiUrlEndPoints.GET_SUB_CITY,this)
    }

    override fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        cityList.value = objTalukaResponseMain.objTalukaDetailResponse.objTalukaDataList as ArrayList<ObjTalukaDataList>
    }

    override fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        iAddPropertyListener.onCityListApiFailure(objTalukaResponseMain)
    }

    override fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain) {
        subAreaList.value = response.objGetCityAreaDetailsResponse.objCityAreaData as ArrayList<ObjCityAreaData>
    }

    override fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain) {
        iAddPropertyListener.onSubAreaListApiFailure(responseMain)
    }

    override fun onAddPropertySuccess(addPropertyMainResponse: AddPropertyMainResponse) {
        iAddPropertyListener.onSuccessAddProperty(addPropertyMainResponse)
    }

    override fun onAddPropertyFailure(addPropertyMainResponse: AddPropertyMainResponse) {
        iAddPropertyListener.onFailureAddProperty(addPropertyMainResponse)
    }

    override fun onSuccessEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
      iAddPropertyListener.onSuccessEditProperty(editPropertyMainResponse)
    }

    override fun onFailureEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
       iAddPropertyListener.onFailureEditProperty(editPropertyMainResponse)
    }

    override fun onGetImagesSuccess(getImageMainResponse: GetImageMainResponse) {
        iGetImagesListener.onSuccessGetImages(getImageMainResponse)
    }

    override fun onGetImageFailure(getImageMainResponse: GetImageMainResponse) {
        iGetImagesListener.onFailureGetImages(getImageMainResponse)
    }

    override fun onDeleteImageSuccess(deleteImageMainResponse: DeleteImageMainResponse) {
       iGetImagesListener.onSuccessDeleteImage(deleteImageMainResponse)
    }

    override fun onDeleteImageFailure(deleteImageMainResponse: DeleteImageMainResponse) {
       iGetImagesListener.onFailureDeleteImage(deleteImageMainResponse)
    }

    override fun networkFailure() {
       iAddPropertyListener.onUserNotConnected()
    }
    fun getPropertyDetails(userId:String,propertyId:String){
        PropertyDetailsRepository.callGetPropertyDetails(mContext,userId,propertyId,
            ApiUrlEndPoints.GET_PROPERTY,this)
    }

    override fun getPropertyDetailsSuccessResponse(propertyDataResponseMain: PropertyDataResponseMain) {
        iPropertyDetailsListener.onSuccessGetPropertyDetails(propertyDataResponseMain)
    }

    override fun getPropertyDetailsFailureResponse(propertyDataResponseMain: PropertyDataResponseMain) {
        iPropertyDetailsListener.onFailureGetPropertyDetails(propertyDataResponseMain)
    }

}