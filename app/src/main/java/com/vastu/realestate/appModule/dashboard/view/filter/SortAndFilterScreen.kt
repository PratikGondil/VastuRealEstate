package com.vastu.realestate.appModule.dashboard.view.filter

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
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
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.filter.ObjFilterTypeData
import com.vastu.realestatecore.model.filter.ObjFilterTypeList
import com.vastu.realestatecore.model.filter.ObjManageFilterVisibility
import com.vastu.realestatecore.model.request.ObjFilterData


class SortAndFilterScreen(var callback:RealEstateFragment): BottomSheetDialogFragment() , IFilterTypeClickListener, IFilterViewHandler {
    lateinit var multipleFiltersBinding: MultipleFiltersBinding
    lateinit var filterViewModel: RealEstateViewModel
    lateinit var itemsList : ArrayList<ObjFilterTypeList>
    lateinit var customAdapter: FilterTypeAdapter
    lateinit var chipGroup :ChipGroup
    var objFilterData = ObjFilterData()
     var furnishingStatus:ArrayList<String> = arrayListOf()
     var constructionStatus:ArrayList<String> = arrayListOf()

    var objSubAreaReq = ObjSubAreaReq()

    private lateinit var objVerifyDetails:ObjVerifyDtls
   lateinit var subAreaListAdapter : SubAreaListAdapter
    var objManageFilterVisibility= ObjManageFilterVisibility()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
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
        var dMetrics = resources.getDisplayMetrics()
        val h = Math.round(dMetrics.heightPixels / dMetrics.density)
        layoutParams.height = h*2

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
        getSubAreaList()
        observeSubAreaList()
    }
    fun setFilterView(){
        val recyclerView: RecyclerView = multipleFiltersBinding.filterType
        itemsList =  prepareItems()
        customAdapter = FilterTypeAdapter(requireContext(),itemsList,this)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
    }

   fun getSubAreaList(){
       objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!

       objSubAreaReq = ObjSubAreaReq().copy(talukaId = objVerifyDetails.city )
       filterViewModel.callSubAreaListApi(objSubAreaReq)
   }
    private fun prepareItems(): ArrayList<ObjFilterTypeList> {
        val filterTypeList = Gson().fromJson(requireContext().let { BaseUtils.getFilterTypeList(it) }.toString(), ObjFilterTypeData::class.java)
            return filterTypeList.objFilterTypeList
        }

    override fun onFilterItemClickListener(selectedFilterType: String) {
        Log.d("selected",selectedFilterType)

        when(selectedFilterType){
            resources.getString(R.string.by_area)->{
                objManageFilterVisibility = ObjManageFilterVisibility( title = resources.getString(R.string.choose_area))

            }
            resources.getString(R.string.by_budget)->
            {
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false , isVisibleBudgetLayout = true, title = resources.getString(R.string.choose_a_range_below))

            }
            resources.getString(R.string.by_property_type)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false,isVisiblePropertyLayout = true,title = resources.getString(R.string.choose_from_below_options))
//
//            resources.getString(R.string.by_price_per_sq_ft)->{
//                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisiblePricePerSqFtLayout = true,title = resources.getString(R.string.choose_a_range_below_per_Sq))
//            }

            resources.getString(R.string.by_bedrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByBedroomsLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_bathrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByBathroomsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

//            resources.getString(R.string.by_furnishing)->
//                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByFurnishingLayout = true,title = resources.getString(R.string.choose_from_below_options))
//
//            resources.getString(R.string.by_constr_status)->
//                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleConstructionStsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_listed_by)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleListedLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_buildup_area)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleBuildupAreaLayout = true,title = resources.getString(R.string.choose_a_range_below_Sq))

            resources.getString(R.string.change_sort)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleChangeSortLayout = true ,title = resources.getString(R.string.select_from_below_options))


        }

        filterViewModel.setSelectedView(objManageFilterVisibility)


    }

    override fun addFilterChip(text: String) {
        val chip = Chip(requireContext())

        chip.text = text
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener{
            chipGroup.removeView(chip)
    }
        chipGroup.addView(chip)
        if(chipGroup.childCount>0){
            chipGroup.visibility = View.VISIBLE
        }

        updateFilterData(chip)


    }
    fun updateFilterData(chip: Chip){
        when(chip.text){
            multipleFiltersBinding.selectedFilterView.checkHouses.text, multipleFiltersBinding.selectedFilterView.checkApartment.text,
            multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text,multipleFiltersBinding.selectedFilterView.checkFarmHouse.text->{
                    filterViewModel.propertyType.add(chip.text as String)
            }

//            multipleFiltersBinding.selectedFilterView.checkFurnished.text,multipleFiltersBinding.selectedFilterView.checkUnfurnished.text,
//            multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text->{
//             furnishingStatus!!.add(chip.text as String)
//            }


//            multipleFiltersBinding.selectedFilterView.checkUnderConst.text,multipleFiltersBinding.selectedFilterView.checkReadyToMove.text,
//            multipleFiltersBinding.selectedFilterView.checkNewLaunch.text->{
//               constructionStatus!!.add(chip.text as String)
//            }

            multipleFiltersBinding.selectedFilterView.checkOwner.text,multipleFiltersBinding.selectedFilterView.checkDealer.text,
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
                for (area in filterViewModel.subAreaList.value!!){
                    if(area.subArea.equals(chip.text as String))
                        filterViewModel.cityList.add(area.areaId)

                }
            }



        }
    }
    override fun removeChip(id: Int) {
        val chipsList = multipleFiltersBinding.cgFilterGroup.childCount

        for(i in 0 until chipsList){
            val filterText = (chipGroup.getChildAt(i) as Chip).text.toString()

            when(id){
                R.id.checkHouses->{
                    if( multipleFiltersBinding.selectedFilterView.checkHouses.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.propertyType.remove(filterText)
                    }
                }
                R.id.checkApartment->{
                    if( multipleFiltersBinding.selectedFilterView.checkApartment.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.propertyType.remove(filterText)
                    }
                }
                R.id.checkBuilderFloor->{
                    if( multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.propertyType.remove(filterText)
                    }
                }
                R.id.check_farm_house->{
                    if( multipleFiltersBinding.selectedFilterView.checkFarmHouse.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.propertyType.remove(filterText)
                    }
                }
//                 R.id.checkFurnished->{
//                    if( multipleFiltersBinding.selectedFilterView.checkFurnished.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                     furnishingStatus.remove(filterText)
//                }
//                R.id.checkUnfurnished ->{
//                    if( multipleFiltersBinding.selectedFilterView.checkUnfurnished.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                    furnishingStatus.remove(filterText)
//
//                }
//                R.id.checkSemiFurnished ->{
//                    if( multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                    furnishingStatus.remove(filterText)
//
//                }
//                R.id.checkUnderConst->{
//                    if( multipleFiltersBinding.selectedFilterView.checkUnderConst.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                    constructionStatus.remove(filterText)
//
//                }
//                R.id.checkReadyToMove->{
//                    if( multipleFiltersBinding.selectedFilterView.checkReadyToMove.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                    constructionStatus.remove(filterText)
//
//                }
//                R.id.checkNewLaunch->{
//                    if( multipleFiltersBinding.selectedFilterView.checkNewLaunch.text.equals(filterText))
//                        chipGroup.removeView(chipGroup.getChildAt(i))
//                    constructionStatus.remove(filterText)
//
//                }
                R.id.check_owner->{
                    if( multipleFiltersBinding.selectedFilterView.checkOwner.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.listedBy.remove(filterText)
                    }

                }
                R.id.check_dealer->{
                    if( multipleFiltersBinding.selectedFilterView.checkDealer.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.listedBy.remove(filterText)
                    }

                }
                R.id.check_builder->{
                if( multipleFiltersBinding.selectedFilterView.checkBuilder.text.equals(filterText)) {
                    chipGroup.removeView(chipGroup.getChildAt(i))
                    filterViewModel.listedBy.remove(filterText)
                }

                }
                R.id.txtBedroomOne ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomOne.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBedrooms.remove(filterText)
                    }

                }
                R.id.txtBedroomTwo->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBedrooms.remove(filterText)
                    }

                }
                R.id.txtBedroomThree->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomThree.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBedrooms.remove(filterText)
                    }

                }
                R.id.txtBedroomFour ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomFour.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBedrooms.remove(filterText)
                    }

                }
                R.id.txtBathroomOne ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBathroomOne.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBathRooms.remove(filterText)
                    }

                }
                R.id.txtBathroomTwo ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.noOfBathRooms.remove(filterText)
                    }

                }

                R.id.txtDatePublished->{
                    if( multipleFiltersBinding.selectedFilterView.txtDatePublished.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.sortBy.remove(filterText)
                    }

                }
                R.id.txtRelevance ->{
                    if( multipleFiltersBinding.selectedFilterView.txtRelevance.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.sortBy.remove(filterText)
                    }

                }
                R.id.txtPriceIncr ->{
                    if( multipleFiltersBinding.selectedFilterView.txtPriceIncr.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.sortBy.remove(filterText)
                    }

                }
                R.id.txtPriceDecr ->{
                    if( multipleFiltersBinding.selectedFilterView.txtPriceDecr.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.sortBy.remove(filterText)
                    }

                }
                R.id.txtDistance ->{
                    if( multipleFiltersBinding.selectedFilterView.txtDistance.text.equals(filterText)) {
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        filterViewModel.sortBy.remove(filterText)
                    }

                }
                R.id.budgetRangeSlider->{
                    if(filterText.contains(resources.getString(R.string.chip_text_budget)))
                        chipGroup.removeView(chipGroup.getChildAt(i))

                }
                R.id.rangeSliderForSqFt->{
                    if(filterText.contains(resources.getString(R.string.chip_text_price_per_sq)))
                        chipGroup.removeView(chipGroup.getChildAt(i))

                }
                R.id.rangeSliderForBuildupAr->{
                    if(filterText.contains(resources.getString(R.string.chip_text_builtup_area)))
                        chipGroup.removeView(chipGroup.getChildAt(i))

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

    override fun applyFilters() {
        try {
            callback.applyFilters()
            this.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    override fun clearFilter(){
        callback.clearFilter()
        this.dismiss()
    }
    fun observeSubAreaList(){
        filterViewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            subAreaListAdapter = SubAreaListAdapter(subAreaList,this)
            val recyclerView: RecyclerView = multipleFiltersBinding.selectedFilterView.rlAreaListRecyclerView
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = subAreaListAdapter
            setFilterView()
        }
    }
    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
//        customProgressDialog.dismiss()
        Toast.makeText(requireContext(),objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()    }

    override fun onSubAreaClickListener(currentArea: ObjCityAreaData,isSelected:Boolean) {
       if(isSelected){
           addFilterChip(currentArea.subArea)
       }
        else{
            removeCitychip(currentArea.areaId)
        }
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
                    filterViewModel.cityList.remove(filterText)
                    break
                }
            }
            break
        }



    }


}