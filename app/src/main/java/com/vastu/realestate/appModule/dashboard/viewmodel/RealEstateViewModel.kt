package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.networkService.util.Constants
import com.vastu.propertycore.callback.request.response.IGetWishlistResponseListener
import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.repository.AddWishlistRepository
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.utils.BaseUtils
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
import com.vastu.realestate.utils.ApiUrlEndPoints.GET_PROPERTY_LIST
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.callback.response.IFilterPropertyListResListener
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.realestatecore.model.filter.ObjManageFilterVisibility
import com.vastu.realestatecore.model.request.ObjFilterData
import com.vastu.realestatecore.model.response.ObjFilterDataResponseMain
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.vastu.realestatecore.repository.FilterPropertyListRepository
import com.vastu.realestatecore.repository.PropertyListRepository

class RealEstateViewModel(application: Application) : AndroidViewModel(application),
    ITalukaResponseListener,IGetPropertyListResListener, ISubAreaResponseListener,
    IFilterPropertyListResListener, IGetWishlistResponseListener {
    var isFilterViewVisible = ObservableField(View.GONE)
    var isRealEstateVisible = ObservableField(View.VISIBLE)


    lateinit var iRealEstateListener: IRealEstateListener

    var mContext :Application
    init {
        mContext = application
    }
    var title = ObservableField(mContext.resources.getString(R.string.choose_area))
    var range = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.budget_range_icon))
    var lowerLimit = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_lower_limit)))
    var upperLimit = ObservableField(BaseUtils.amountFormatter(mContext.resources.getInteger(R.integer.budget_upper_limit)))
    var budgetSliderLowerLimit = ObservableField(
        BaseUtils.amountFormatter(mContext.resources.getInteger(
            R.integer.budget_lower_limit)))
    var lowerLimitForPerSq = ObservableField(
        BaseUtils.amountFormatter(mContext.resources.getInteger(
            R.integer.budget_lower_limit)))
    var upperLimitForPerSq = ObservableField(
        BaseUtils.amountFormatter(mContext.resources.getInteger(
            R.integer.SqFt_upper_limit)))
    var lowerLimitForBuildupArea = ObservableField(
        BaseUtils.amountFormatter(mContext.resources.getInteger(
            R.integer.budget_lower_limit)))
    var upperLimitForBuildupArea = ObservableField(
        BaseUtils.amountFormatter(mContext.resources.getInteger(
            R.integer.buildup_area_upper_limit)))
    var isHousesSelected = ObservableField(false)
    var isApartmentSelected = ObservableField(false)
    var isBuilerFloorSelected = ObservableField(false)
    var isFurnished = ObservableField(false)
    var isUnfurnished = ObservableField(false)
    var isSemiFurnished = ObservableField(false)
    var isUnderConst = ObservableField(false)
    var isReadyToMove = ObservableField(false)
    var isNewLaunch = ObservableField(false)
    var isOwner = ObservableField(false)
    var isDealer = ObservableField(false)
    var isBuilder = ObservableField(false)

    var isFarmHousesSelected = ObservableField(false)
    var isVisibleCityLayout = ObservableField(false)
    var isVisibleSubAreaLayout = ObservableField(false)
    var isVisibleBudgetLayout = ObservableField(false)
    var isVisiblePropertyLayout = ObservableField(false)
    var isVisiblePricePerSqFtLayout = ObservableField(false)
    var isVisibleByBedroomsLayout = ObservableField(false)
    var isVisibleByBathroomsLayout = ObservableField(false)
    var isVisibleByFurnishingLayout = ObservableField(false)
    var isVisibleConstructionStsLayout = ObservableField(false)
    var isVisibleListedLayout = ObservableField(false)
    var isVisibleBuildupAreaLayout = ObservableField(false)
    var isVisibleChangeSortLayout = ObservableField(false)
    lateinit var iFilterViewHandler : IFilterViewHandler
    var budgetLimit :ArrayList<String> = arrayListOf()
    var buildUpAreaLimits :ArrayList<String> = arrayListOf()
    var subAreaList = MutableLiveData<ArrayList<ObjCityAreaData>>()
    var propertyType:ArrayList<String> = arrayListOf()
    var listedBy:ArrayList<String> = arrayListOf()
    var noOfBedrooms:ArrayList<String> = arrayListOf()
    var noOfBathRooms:ArrayList<String> = arrayListOf()
    var sortBy:ArrayList<String> = arrayListOf()
    var selectedAreaList :ArrayList<String> = arrayListOf()
    var selectedCityList :ArrayList<String> = arrayListOf()
    var selectedCity = MutableLiveData<ObjTalukaDataList>()

    var cityListResponse = MutableLiveData<ArrayList<ObjTalukaDataList>>()

    lateinit var iFilterClickListener: IFilterClickListener
    fun getPropertyList(userId:String,endpoint:String){
        PropertyListRepository.callGetPropertyList(mContext,userId,endpoint,this)
    }
    fun filterClick(){
        iFilterClickListener.setFilterView()
    }

    override fun getPropertyListSuccessResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        iRealEstateListener.onSuccessGetRealEstateList(objGetPropertyListResMain)
    }
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        iRealEstateListener.searchFilter(s.toString())
    }
    override fun getPropertyListFailureResponse(objGetPropertyListResMain: ObjGetPropertyListResMain) {
       iRealEstateListener.onFailureGetRealEstateList(objGetPropertyListResMain)
    }

    override fun onTalukaListSuccessResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        cityListResponse.value = objTalukaResponseMain.objTalukaDetailResponse.objTalukaDataList as ArrayList<ObjTalukaDataList>
    }

    override fun onTalukaListFailureResponse(objTalukaResponseMain: ObjTalukaResponseMain) {
        iFilterViewHandler.onCityListApiFailure(objTalukaResponseMain)
    }

    override fun networkFailure() {
      iRealEstateListener.onUserNotConnected()
    }
    fun setSelectedView(objManageFilterVisibility: ObjManageFilterVisibility){
        title.set(objManageFilterVisibility.title)
        isVisibleCityLayout.set(objManageFilterVisibility.isVisibleCityLayout)
        isVisibleSubAreaLayout.set(objManageFilterVisibility.isVisibleSubAreaLayout)
        isVisibleBudgetLayout.set(objManageFilterVisibility.isVisibleBudgetLayout)
        isVisiblePropertyLayout.set(objManageFilterVisibility.isVisiblePropertyLayout)
        isVisiblePricePerSqFtLayout.set(objManageFilterVisibility.isVisiblePricePerSqFtLayout)
        isVisibleByBedroomsLayout.set(objManageFilterVisibility.isVisibleByBedroomsLayout)
        isVisibleByBathroomsLayout.set(objManageFilterVisibility.isVisibleByBathroomsLayout)
        isVisibleByFurnishingLayout.set(objManageFilterVisibility.isVisibleByFurnishingLayout)
        isVisibleConstructionStsLayout.set(objManageFilterVisibility.isVisibleConstructionStsLayout)
        isVisibleListedLayout.set(objManageFilterVisibility.isVisibleListedLayout)
        isVisibleBuildupAreaLayout.set(objManageFilterVisibility.isVisibleBuildupAreaLayout)
        isVisibleChangeSortLayout.set(objManageFilterVisibility.isVisibleChangeSortLayout)
    }
    fun callCityListApi(){
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        CityListRequestRepository.callCityListApi(mContext, ApiUrlEndPoints.GET_CITIES,language!!,this)
    }

    fun callSubAreaListApi(talukaId: ObjSubAreaReq){
        SubAreaRequestRepository.callSubAreaListApi(mContext,talukaId, ApiUrlEndPoints.GET_SUB_CITY,this)

    }    fun addChip(text:String){
        iFilterViewHandler.addFilterChip(text)
    }
    fun removeChip(id: Int) {
        iFilterViewHandler.removeChip(id)
    }

    fun applyFilters(){
        iFilterViewHandler.applyFilters()
    }
    fun clearFilter(){
        iFilterViewHandler.clearFilter()
    }
    fun callApplyFilterApi(objFilterData: ObjFilterData){
        FilterPropertyListRepository.getFilterPropertyList(mContext,objFilterData,"filter.php",this)
    }
    override fun onGetSubAreaResponseSuccess(response: ObjGetCityAreaDetailResponseMain) {
        subAreaList.value = response.objGetCityAreaDetailsResponse.objCityAreaData as ArrayList<ObjCityAreaData>
    }

    override fun onGetSubAreaResponseFailure(responseMain: ObjGetCityAreaDetailResponseMain) {
        iFilterViewHandler.onSubAreaListApiFailure(responseMain)
    }
    override fun onSuccessFilterList(objFilterDataResponseMain: ObjFilterDataResponseMain) {
        iRealEstateListener.onFilterPropertyListSuccess(objFilterDataResponseMain.objGetFilterDataResponse)
    }

    override fun onFailureFilterList(objFilterDataResponseMain: ObjFilterDataResponseMain) {
        iRealEstateListener.onFilterPropertyListFailure(objFilterDataResponseMain)
    }

    fun getAddToWishlist(userId:String,propertyId:String){
        AddWishlistRepository.callAddWishlistApi(mContext,userId,propertyId,
            ApiUrlEndPoints.ADD_WISHLIST,this)
    }

    override fun getWishlistSuccessResponse(addWishlistResponse: AddWishlistResponse) {
        iRealEstateListener.onSuccessAddWishList(addWishlistResponse)
    }

    override fun getWishlistFailureResponse(addWishlistResponse: AddWishlistResponse) {
        iRealEstateListener.onFailureAddWishList(addWishlistResponse)
    }
}