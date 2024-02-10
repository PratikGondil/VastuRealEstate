package com.vastu.realestate.appModule.dashboard.view.filter

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.SubAreaListAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterTypeClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.dashboard.view.RealEstateFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.MultipleFiltersBinding
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.filter.ObjFilterTypeData
import com.vastu.realestatecore.model.filter.ObjFilterTypeList
import com.vastu.realestatecore.model.filter.ObjManageFilterVisibility
import com.vastu.realestatecore.model.request.ObjFilterData


class SortAndFilterScreen(var callback:RealEstateFragment): DialogFragment() , IFilterTypeClickListener, IFilterViewHandler,View.OnTouchListener {
    lateinit var multipleFiltersBinding: MultipleFiltersBinding
    lateinit var filterViewModel: RealEstateViewModel
    lateinit var itemsList : ArrayList<ObjFilterTypeList>
    lateinit var customAdapter: FilterTypeAdapter
    lateinit var chipGroup :ChipGroup
    lateinit var objFilterData:ObjFilterData
     var furnishingStatus:ArrayList<String> = arrayListOf()
     var constructionStatus:ArrayList<String> = arrayListOf()

    var objSubAreaReq = ObjSubAreaReq()

    private lateinit var objVerifyDetails:ObjVerifyDtls
   lateinit var subAreaListAdapter : SubAreaListAdapter
    var objManageFilterVisibility= ObjManageFilterVisibility()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as Dialog
            getDialog()!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                behaviour.expandedOffset = 50
               setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }
        return dialog
    }
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        var dMetrics = resources.displayMetrics
        val h = Math.round(dMetrics.heightPixels / dMetrics.density)
        val w = Math.round(dMetrics.widthPixels / dMetrics.density)
        layoutParams.height = (h*1.9).toInt()
        layoutParams.width= (w * 1.9).toInt()
        bottomSheet.layoutParams = layoutParams

    }
override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        filterViewModel = ViewModelProvider(this)[RealEstateViewModel::class.java]
        multipleFiltersBinding = DataBindingUtil.inflate(inflater, R.layout.multiple_filters,container,false)
//        tabLayout = multipleFiltersBinding.filterType
        multipleFiltersBinding.lifecycleOwner = this
        multipleFiltersBinding.realEstateViewModel = filterViewModel
        filterViewModel.iFilterViewHandler = this
        iniView()

    chipGroup = multipleFiltersBinding.cgFilterGroup
        return multipleFiltersBinding.root
    }

    fun iniView(){
        multipleFiltersBinding.selectedFilterView.autoCompleteCity.setOnTouchListener(this)
        getCityList()
        observeCityList()
        observeCity()
//        getSubAreaList()
        observeSubAreaList()

    }
    fun setFilterView(){
        val recyclerView: RecyclerView = multipleFiltersBinding.filterType
        itemsList =  prepareItems()
        customAdapter = FilterTypeAdapter(requireContext(),itemsList,this)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

        if(PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!= null) {
            objFilterData = PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!!
            if (this::objFilterData.isInitialized) {
                setPreviousData(objFilterData)
            }
        }
    }
    fun getCityList(){
        filterViewModel.callCityListApi()
    }

   fun getSubAreaList(){
       var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
       objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!

       objSubAreaReq = ObjSubAreaReq().copy(talukaId = objVerifyDetails.city,language = language )
       filterViewModel.callSubAreaListApi(objSubAreaReq)
   }
    private fun prepareItems(): ArrayList<ObjFilterTypeList> {
        val filterTypeList = Gson().fromJson(requireContext().let { BaseUtils.getFilterTypeList(it) }.toString(), ObjFilterTypeData::class.java)
            return filterTypeList.objFilterTypeList
        }

    override fun onFilterItemClickListener(selectedFilterType: String) {
        Log.d("selected",selectedFilterType)

        when(selectedFilterType){
            resources.getString(R.string.district)->{
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, title = resources.getString(R.string.choose_from_below_options))

            }
            resources.getString(R.string.by_taluka)->{
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = true, title = resources.getString(R.string.choose_city))

            }
            resources.getString(R.string.by_area)->{
                objManageFilterVisibility = ObjManageFilterVisibility( isVisibleCityLayout = false ,isVisibleSubAreaLayout = true,title = resources.getString(R.string.choose_area))

            }
            resources.getString(R.string.by_budget)->
            {
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false , isVisibleBudgetLayout = true, title = resources.getString(R.string.choose_a_range_below))

            }
            resources.getString(R.string.by_property_type)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false,isVisiblePropertyLayout = true,title = resources.getString(R.string.choose_from_below_options))
//
//            resources.getString(R.string.by_price_per_sq_ft)->{
//                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisiblePricePerSqFtLayout = true,title = resources.getString(R.string.choose_a_range_below_per_Sq))
//            }

            resources.getString(R.string.by_bedrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleByBedroomsLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_bathrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleByBathroomsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_furnishing)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleByFurnishingLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_constr_status)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleConstructionStsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_listed_by)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleListedLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_buildup_area)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleBuildupAreaLayout = true,title = resources.getString(R.string.choose_a_range_below_Sq))

            resources.getString(R.string.change_sort)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleCityLayout = false, isVisibleChangeSortLayout = true ,title = resources.getString(R.string.select_from_below_options))


        }

        filterViewModel.setSelectedView(objManageFilterVisibility)


    }

    override fun addFilterChip(text: String) {
        val chip = Chip(requireContext())

        chip.text = text
        chip.isCloseIconVisible = false
//        chip.setOnCloseIconClickListener{
//            chipGroup.removeView(chip)
//    }

        if(!checkIsAlreadyExist(text)){
          chipGroup.addView(chip)
          updateFilterData(chip)
        }

        if(chipGroup.childCount>0){
            chipGroup.visibility = View.VISIBLE
        }

    }
    fun checkIsAlreadyExist(text: String): Boolean {
        val chipsList = multipleFiltersBinding.cgFilterGroup
        var isExist=false
        if(chipsList.size > 0){
            for(i in 1 until chipsList.size){
                var key = (chipsList.getChildAt(i) as Chip).text.toString()
                if(key.equals(text)){
//                    ||key.contains(resources.getString(R.string.chip_text_budget)) || key.contains(resources.getString(R.string.chip_text_builtup_area))) {
                    isExist =true

                }
            }

        }
        return isExist
    }
    fun updateFilterData(chip: Chip){
        when(chip.text){

            multipleFiltersBinding.selectedFilterView.checkHouses.text, multipleFiltersBinding.selectedFilterView.checkApartment.text,
            multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text,multipleFiltersBinding.selectedFilterView.checkFarmHouse.text->{
                    filterViewModel.propertyType.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.checkFurnished.text,multipleFiltersBinding.selectedFilterView.checkUnfurnished.text,
            multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text->{
             furnishingStatus!!.add(chip.text as String)
            }


            multipleFiltersBinding.selectedFilterView.checkUnderConst.text,multipleFiltersBinding.selectedFilterView.checkReadyToMove.text,
            multipleFiltersBinding.selectedFilterView.checkNewLaunch.text->{
               constructionStatus!!.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.checkOwner.text,
            multipleFiltersBinding.selectedFilterView.checkBuilder.text->{
                filterViewModel.listedBy.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.txtBedroomOne.text,multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text ,
            multipleFiltersBinding.selectedFilterView.txtBedroomThree.text,multipleFiltersBinding.selectedFilterView.txtBedroomFour.text->{
                filterViewModel.noOfBedrooms.add((chip.text as String).substring(0,1))

            }

            multipleFiltersBinding.selectedFilterView.txtBathroomOne.text,multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text ->{
                filterViewModel.noOfBathRooms.add((chip.text as String).substring(0,1))
            }

            multipleFiltersBinding.selectedFilterView.txtDatePublished.text, multipleFiltersBinding.selectedFilterView.txtRelevance.text,
            multipleFiltersBinding.selectedFilterView.txtPriceIncr.text,multipleFiltersBinding.selectedFilterView.txtPriceDecr.text,
            multipleFiltersBinding.selectedFilterView.txtDistance.text->{
                filterViewModel.sortBy.add(chip.text as String)
            }

            else->{
                if(chip.text.contains(resources.getString(R.string.chip_text_budget))){
                    filterViewModel.budgetLimit.add(filterViewModel.lowerLimit.get()!!)
                    filterViewModel.budgetLimit.add(filterViewModel.upperLimit.get()!!)
                }
                else if(chip.text.contains(resources.getString(R.string.chip_text_builtup_area))){
                    filterViewModel.buildUpAreaLimits.add(filterViewModel.lowerLimitForBuildupArea.get()!!)
                    filterViewModel.buildUpAreaLimits.add( filterViewModel.upperLimitForBuildupArea.get()!!)
                }
                else{
                for (area in filterViewModel.subAreaList.value!!) {
                    if (area.subArea.equals(chip.text as String))
                        filterViewModel.selectedAreaList.add(area.areaId)
                }

                }
            }



        }
    }
    override fun removeChip(id: Int) {
        val chipsList = multipleFiltersBinding.cgFilterGroup.childCount
        if(chipsList > 0) {
            for (i in 0 until chipsList) {
                val filterText = (chipGroup.getChildAt(i) as Chip).text.toString()

                when (id) {
                    R.id.checkHouses -> {
                        if (multipleFiltersBinding.selectedFilterView.checkHouses.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.propertyType.remove(filterText)
                            break
                        }
                    }
                    R.id.checkApartment -> {
                        if (multipleFiltersBinding.selectedFilterView.checkApartment.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.propertyType.remove(filterText)
                            break
                        }
                    }
                    R.id.checkBuilderFloor -> {
                        if (multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.propertyType.remove(filterText)
                            break
                        }
                    }
                    R.id.check_farm_house -> {
                        if (multipleFiltersBinding.selectedFilterView.checkFarmHouse.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.propertyType.remove(filterText)
                            break
                        }
                    }
                    R.id.checkFurnished -> {
                        if (multipleFiltersBinding.selectedFilterView.checkFurnished.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            furnishingStatus.remove(filterText)
                            break
                        }
                    }
                    R.id.checkUnfurnished -> {
                        if (multipleFiltersBinding.selectedFilterView.checkUnfurnished.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            furnishingStatus.remove(filterText)
                            break
                        }

                    }
                    R.id.checkSemiFurnished -> {
                        if (multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            furnishingStatus.remove(filterText)
                            break
                        }

                    }
                    R.id.checkUnderConst -> {
                        if (multipleFiltersBinding.selectedFilterView.checkUnderConst.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            constructionStatus.remove(filterText)
                            break
                        }

                    }
                    R.id.checkReadyToMove -> {
                        if (multipleFiltersBinding.selectedFilterView.checkReadyToMove.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            constructionStatus.remove(filterText)
                            break
                        }
                    }
                    R.id.checkNewLaunch -> {
                        if (multipleFiltersBinding.selectedFilterView.checkNewLaunch.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            constructionStatus.remove(filterText)
                            break
                        }

                    }
                    R.id.check_owner -> {
                        if (multipleFiltersBinding.selectedFilterView.checkOwner.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.listedBy.remove(filterText)
                            break
                        }

                    }
                    R.id.check_builder -> {
                        if (multipleFiltersBinding.selectedFilterView.checkBuilder.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.listedBy.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBedroomOne -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBedroomOne.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBedrooms.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBedroomTwo -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBedrooms.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBedroomThree -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBedroomThree.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBedrooms.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBedroomFour -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBedroomFour.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBedrooms.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBathroomOne -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBathroomOne.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBathRooms.remove(filterText)
                            break
                        }

                    }
                    R.id.txtBathroomTwo -> {
                        if (multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.noOfBathRooms.remove(filterText)
                            break
                        }

                    }

                    R.id.txtDatePublished -> {
                        if (multipleFiltersBinding.selectedFilterView.txtDatePublished.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.sortBy.remove(filterText)
                            break
                        }

                    }
                    R.id.txtRelevance -> {
                        if (multipleFiltersBinding.selectedFilterView.txtRelevance.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.sortBy.remove(filterText)
                            break
                        }

                    }
                    R.id.txtPriceIncr -> {
                        if (multipleFiltersBinding.selectedFilterView.txtPriceIncr.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.sortBy.remove(filterText)
                            break

                        }

                    }
                    R.id.txtPriceDecr -> {
                        if (multipleFiltersBinding.selectedFilterView.txtPriceDecr.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.sortBy.remove(filterText)
                            break
                        }

                    }
                    R.id.txtDistance -> {
                        if (multipleFiltersBinding.selectedFilterView.txtDistance.text.equals(
                                filterText
                            )
                        ) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            filterViewModel.sortBy.remove(filterText)
                            break
                        }

                    }
                    R.id.budgetRangeSlider -> {
                        if (filterText.contains(resources.getString(R.string.chip_text_budget))) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            break
                        }
                    }
                    R.id.rangeSliderForSqFt -> {
                        if (filterText.contains(resources.getString(R.string.chip_text_price_per_sq))) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            break
                        }
                    }
                    R.id.rangeSliderForBuildupAr -> {
                        if (filterText.contains(resources.getString(R.string.chip_text_builtup_area))) {
                            chipGroup.removeView(chipGroup.getChildAt(i))
                            break
                        }

                    }
//                else->{
//                    for (area in filterViewModel.subAreaList.value!!){
//                        if(area.subArea.equals(filterText))
//                            chipGroup.removeView(chipGroup.getChildAt(i))
//                        cityList.remove(filterText)
//                    }
//                }

                }
            }
        }
    }

    override fun applyFilters() {
        try {
            if(filterViewModel.selectedAreaList.size == 0)
                filterViewModel.selectedAreaList.add("")

            if(filterViewModel.budgetLimit.size == 0)
                filterViewModel.budgetLimit.add("")

            if(filterViewModel.propertyType.size == 0)
                filterViewModel.propertyType.add("")

            if(filterViewModel.noOfBathRooms.size == 0)
                filterViewModel.noOfBathRooms.add("")

            if(filterViewModel.noOfBedrooms.size == 0)
                filterViewModel.noOfBedrooms.add("")

            if(filterViewModel.listedBy.size == 0)
                filterViewModel.listedBy.add("")

            if(filterViewModel.buildUpAreaLimits.size == 0)
                filterViewModel.buildUpAreaLimits.add("")

            if(filterViewModel.sortBy.size == 0)
                filterViewModel.sortBy.add("")

                if (filterViewModel.selectedCity.value != null) {
                    filterViewModel.selectedCity.value!!.talukaId?.let {
                        filterViewModel.selectedCityList.add(
                            it
                        )
                    }
                    }
            else{
                    filterViewModel.selectedCityList.add("")
            }

            objFilterData =  ObjFilterData().copy(city= filterViewModel.selectedCityList,subAreaId =filterViewModel.selectedAreaList, budget = filterViewModel.budgetLimit, propertyType = filterViewModel.propertyType,
                noOfBathrooms = filterViewModel.noOfBathRooms, noOfBedrooms = filterViewModel.noOfBedrooms, listedBy = filterViewModel.listedBy, buildUpArea = filterViewModel.buildUpAreaLimits,
                sortBy = filterViewModel.sortBy
            )
            callback.applyFilters(objFilterData)
            this.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    override fun clearFilter(){
        callback.clearFilter()
        this.dismiss()
    }
    fun observeCityList(){
        filterViewModel.cityListResponse.observe(viewLifecycleOwner) { cityList ->
                val adapter: java.util.ArrayList<ObjTalukaDataList> =cityList
                multipleFiltersBinding.selectedFilterView.autoCompleteCity.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.drop_down_item, adapter
                    )
                )
            setFilterView()
        }

    }
    private fun observeCity(){
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        filterViewModel.selectedCity.observe(viewLifecycleOwner){city->
            if (city != null) {
                objSubAreaReq = ObjSubAreaReq().copy(city.talukaId, language = language )
                filterViewModel.callSubAreaListApi(objSubAreaReq)
            }
        }
    }
    fun observeSubAreaList(){
        filterViewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            subAreaListAdapter = SubAreaListAdapter(subAreaList,this)
            val recyclerView: RecyclerView = multipleFiltersBinding.selectedFilterView.rlAreaListRecyclerView
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = subAreaListAdapter
//            setFilterView()
//            if(PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!= null) {
//                objFilterData = PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!!
//                if (this::objFilterData.isInitialized) {
//                    setPreviousData(objFilterData)
//                }
//            }
        }
    }
    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
//        customProgressDialog.dismiss()
        Toast.makeText(requireContext(),objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()
    }

    override fun onSubAreaClickListener(currentArea: ObjCityAreaData,isSelected:Boolean) {
       if(isSelected){
           addFilterChip(currentArea.subArea)
       }
        else{
            removeCitychip(currentArea.areaId)
        }
    }

    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
        callback.onErrorResponse(objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr, isSuccess = false, isNetworkFailure = false)
         }

//    override fun onPropertyListSuccess(objGetFilterDataResponse: ObjGetFilterDataResponse) {
//
//    }
//
//    override fun onPropertyListFailure(objFilterDataResponseMain: ObjFilterDataResponseMain) {
//        TODO("Not yet implemented")
//    }

    fun removeCitychip(areaId :String){
        val chipsList = multipleFiltersBinding.cgFilterGroup.childCount

        for(i in 0 until chipsList) {
            val filterText = (chipGroup.getChildAt(i) as Chip).text.toString()
            for (area in filterViewModel.subAreaList.value!!){
                if(areaId.equals(area.areaId) && filterText.equals(area.subArea)) {
                    chipGroup.removeView(chipGroup.getChildAt(i))
                    filterViewModel.selectedAreaList.remove(filterText)
                    break
                }
            }
            break
        }



    }

    fun setPreviousData(objFilterData: ObjFilterData?) {
        if(objFilterData!!.budget[0].isNotEmpty()) {
            filterViewModel.lowerLimit.set(objFilterData.budget[0])
            filterViewModel.upperLimit.set(objFilterData.budget[1])
        }
        if(objFilterData.buildUpArea[0].isNotEmpty()) {
            filterViewModel.lowerLimitForBuildupArea.set(objFilterData.buildUpArea[0])
            filterViewModel.upperLimitForBuildupArea.set(objFilterData.buildUpArea[1])
        }
        if(objFilterData.subAreaId[0].isNotEmpty()) {
            setListValues(objFilterData.subAreaId, filterViewModel.selectedAreaList)
            //setSelectedSubarea()

        }
        if(objFilterData.propertyType[0].isNotEmpty()) {
            setListValues(objFilterData.propertyType, filterViewModel.propertyType)
            setChecks(filterViewModel.propertyType)
        }
//        for (i in 0 until objFilterData.subAreaId.size){
//            filterViewModel.cityList.add(objFilterData.subAreaId[i])
//        }
        if(objFilterData.noOfBathrooms[0].isNotEmpty()) {
            setListValues(objFilterData.noOfBathrooms, filterViewModel.noOfBathRooms)
            setNoOfBathRooms(filterViewModel.noOfBathRooms)

        }
        if(objFilterData.noOfBedrooms[0].isNotEmpty()) {
            setListValues(objFilterData.noOfBedrooms, filterViewModel.noOfBedrooms)
            setNoOfBedRooms(filterViewModel.noOfBedrooms)

        }

        if(objFilterData.listedBy[0].isNotEmpty()) {
            setListValues(objFilterData.listedBy, filterViewModel.listedBy)
            setChecks(filterViewModel.listedBy)

        }

        if(objFilterData.sortBy[0].isNotEmpty()) {
            setListValues(objFilterData.sortBy, filterViewModel.sortBy)
            setChecks(filterViewModel.sortBy)

        }
    }
    fun setListValues(source :ArrayList<String>,destination:ArrayList<String>){
        for (i in 0 until source.size){
            destination.add(source[i])
        }

    }
    fun setSelectedSubarea(){

        for(i in 0 until  filterViewModel.selectedAreaList.size) {
            if (filterViewModel.subAreaList.value!! !=null) {
                for (area in filterViewModel.subAreaList.value!!) {
                    if (filterViewModel.selectedAreaList[i].equals(area.areaId))
                        addFilterChip(area.subArea)
                }
            }
        }
    }
    fun setChecks (list:ArrayList<String>) {
        for (i in 0 until list.size) {
            when(list[i]){
                multipleFiltersBinding.selectedFilterView.checkHouses.text-> {
                    filterViewModel.isHousesSelected.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkApartment.text-> {
                    filterViewModel.isApartmentSelected.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text-> {
                    filterViewModel.isBuilerFloorSelected.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkFarmHouse.text-> {
                    filterViewModel.isFarmHousesSelected.set(true)
                    addFilterChip(list[i])
                }

                multipleFiltersBinding.selectedFilterView.checkFurnished.text-> {
                    filterViewModel.isFurnished.set(true)
                    addFilterChip(list[i])
                }

                multipleFiltersBinding.selectedFilterView.checkUnfurnished.text-> {
                    filterViewModel.isUnfurnished.set(true)
                    addFilterChip(list[i])
                }

                multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text-> {
                    filterViewModel.isSemiFurnished.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkUnderConst.text-> {
                    filterViewModel.isUnderConst.set(true)
                    addFilterChip(list[i])
                }

                multipleFiltersBinding.selectedFilterView.checkReadyToMove.text-> {
                    filterViewModel.isReadyToMove.set(true)
                    addFilterChip(list[i])
                }

                multipleFiltersBinding.selectedFilterView.checkNewLaunch.text-> {
                    filterViewModel.isNewLaunch.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkOwner.text-> {
                    filterViewModel.isOwner.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.checkBuilder.text-> {
                    filterViewModel.isBuilder.set(true)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtBedroomOne.text->{
                    multipleFiltersBinding.selectedFilterView.txtBedroomOne.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text ->{
                    multipleFiltersBinding.selectedFilterView.txtBedroomTwo.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtBedroomThree.text->{
                    multipleFiltersBinding.selectedFilterView.txtBedroomThree.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }multipleFiltersBinding.selectedFilterView.txtBedroomFour.text->{
                multipleFiltersBinding.selectedFilterView.txtBedroomFour.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtBathroomOne.text->{
                    multipleFiltersBinding.selectedFilterView.txtBathroomOne.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text ->{
                    multipleFiltersBinding.selectedFilterView.txtBathroomTwo.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtDatePublished.text->{
                    multipleFiltersBinding.selectedFilterView.txtDatePublished.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                    multipleFiltersBinding.selectedFilterView.txtRelevance.text->{
                        multipleFiltersBinding.selectedFilterView.txtRelevance.setBackgroundResource(R.drawable.filter_option_background_onselect)
                        addFilterChip(list[i])
                    }

                multipleFiltersBinding.selectedFilterView.txtPriceIncr.text->{
                    multipleFiltersBinding.selectedFilterView.txtPriceIncr.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtPriceDecr.text->{
                    multipleFiltersBinding.selectedFilterView.txtPriceDecr.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }
                multipleFiltersBinding.selectedFilterView.txtDistance.text->{
                    multipleFiltersBinding.selectedFilterView.txtDistance.setBackgroundResource(R.drawable.filter_option_background_onselect)
                    addFilterChip(list[i])
                }

            }


        }

    }
    fun setNoOfBathRooms(list: ArrayList<String> /* = java.util.ArrayList<kotlin.String> */){
        for (i in 0 until list.size){
            if(multipleFiltersBinding.selectedFilterView.txtBathroomOne.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBathroomOne.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBathroomOne.text as String)
            }
            else if(multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBathroomTwo.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text.toString())
            }
        }
    }
    fun setNoOfBedRooms(list: ArrayList<String>){
        for (i in 0 until list.size){
            if(multipleFiltersBinding.selectedFilterView.txtBedroomOne.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBedroomOne.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBedroomOne.text as String)
            }
            else if(multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBedroomTwo.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text as String)
            }
            else if(multipleFiltersBinding.selectedFilterView.txtBedroomThree.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBedroomThree.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBedroomThree.text as String)
            }
            if(multipleFiltersBinding.selectedFilterView.txtBedroomFour.text.contains(list[i])){
                multipleFiltersBinding.selectedFilterView.txtBedroomFour.setBackgroundResource(R.drawable.filter_option_background_onselect)
                addFilterChip(multipleFiltersBinding.selectedFilterView.txtBedroomFour.text as String)
            }
        }
    }

    fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }

    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == multipleFiltersBinding.selectedFilterView.autoCompleteCity) {
            view.let {
                onShowStateDropDown(it)
            }
        }

        return true
    }

}