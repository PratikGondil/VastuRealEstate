package com.vastu.realestate.appModule.dashboard.view.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.SubAreaListAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterTypeClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.dashboard.viewmodel.FilterViewModel
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
import com.vastu.realestatecore.model.request.*

class SortAndFilterScreen:Fragment() , IFilterTypeClickListener, IFilterViewHandler {
    lateinit var multipleFiltersBinding: MultipleFiltersBinding
    lateinit var filterViewModel: FilterViewModel
    lateinit var itemsList : ArrayList<ObjFilterTypeList>
    lateinit var customAdapter: FilterTypeAdapter
    lateinit var chipGroup :ChipGroup
    var objFilterData = ObjFilterData()
    var propertyType:ArrayList<String> = arrayListOf()
     var furnishingStatus:ArrayList<String> = arrayListOf()
     var constructionStatus:ArrayList<String> = arrayListOf()
     var listedBy:ArrayList<String> = arrayListOf()
     var noOfBedrooms:ArrayList<String> = arrayListOf()
     var noOfBathRooms:ArrayList<String> = arrayListOf()
     var sortBy:ArrayList<String> = arrayListOf()
    var cityList :ArrayList<String> = arrayListOf()
    var objSubAreaReq = ObjSubAreaReq()

    private lateinit var objVerifyDetails:ObjVerifyDtls
   lateinit var subAreaListAdapter : SubAreaListAdapter
    var objManageFilterVisibility= ObjManageFilterVisibility()
override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterViewModel = ViewModelProvider(this)[FilterViewModel::class.java]
        multipleFiltersBinding = DataBindingUtil.inflate(inflater, R.layout.multiple_filters,container,false)
//        tabLayout = multipleFiltersBinding.filterType
        multipleFiltersBinding.lifecycleOwner = this
        multipleFiltersBinding.filterViewModel = filterViewModel
        filterViewModel.iFilterViewHandler = this
        iniView()
    chipGroup = multipleFiltersBinding.cgFilterGroup
        return multipleFiltersBinding.root
    }
    fun iniView(){
        getSubAreaList()
        observeSubAreaList()
//        val recyclerView: RecyclerView = multipleFiltersBinding.filterType
//        itemsList =  prepareItems()
//        customAdapter = FilterTypeAdapter(requireContext(),itemsList,this)
//        val layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = customAdapter
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

            resources.getString(R.string.by_price_per_sq_ft)->{
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisiblePricePerSqFtLayout = true,title = resources.getString(R.string.choose_a_range_below_per_Sq))
            }

            resources.getString(R.string.by_bedrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByBedroomsLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_bathrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByBathroomsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_furnishing)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleByFurnishingLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_constr_status)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleSubAreaLayout = false, isVisibleConstructionStsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

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
                    propertyType!!.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.checkFurnished.text,multipleFiltersBinding.selectedFilterView.checkUnfurnished.text,
            multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text->{
             furnishingStatus!!.add(chip.text as String)
            }


            multipleFiltersBinding.selectedFilterView.checkUnderConst.text,multipleFiltersBinding.selectedFilterView.checkReadyToMove.text,
            multipleFiltersBinding.selectedFilterView.checkNewLaunch.text->{
               constructionStatus!!.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.checkOwner.text,multipleFiltersBinding.selectedFilterView.checkDealer.text,
            multipleFiltersBinding.selectedFilterView.checkBuilder.text->{
                   listedBy!!.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.txtBedroomOne.text,multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text ,
            multipleFiltersBinding.selectedFilterView.txtBedroomThree.text,multipleFiltersBinding.selectedFilterView.txtBedroomFour.text->{
                noOfBedrooms.add((chip.text as String).substring(0,1))

            }

            multipleFiltersBinding.selectedFilterView.txtBathroomOne.text,multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text, ->{
                    noOfBathRooms.add(chip.text as String)
            }

            multipleFiltersBinding.selectedFilterView.txtDatePublished.text, multipleFiltersBinding.selectedFilterView.txtRelevance.text,
            multipleFiltersBinding.selectedFilterView.txtPriceIncr.text,multipleFiltersBinding.selectedFilterView.txtPriceDecr.text,
            multipleFiltersBinding.selectedFilterView.txtDistance.text->{
           sortBy!!.add(chip.text as String)
            }

            else->{
                for (area in filterViewModel.subAreaList.value!!){
                    if(area.subArea.equals(chip.text as String))
                        cityList.add(area.areaId)

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
                    if( multipleFiltersBinding.selectedFilterView.checkHouses.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                        propertyType.remove(filterText)
                }
                R.id.checkApartment->{
                    if( multipleFiltersBinding.selectedFilterView.checkApartment.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    propertyType.remove(filterText)
                }
                R.id.checkBuilderFloor->{
                    if( multipleFiltersBinding.selectedFilterView.checkBuilderFloor.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    propertyType.remove(filterText)
                }
                R.id.check_farm_house->{
                    if( multipleFiltersBinding.selectedFilterView.checkFarmHouse.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    propertyType.remove(filterText)
                }
                 R.id.checkFurnished->{
                    if( multipleFiltersBinding.selectedFilterView.checkFurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                     furnishingStatus.remove(filterText)
                }
                R.id.checkUnfurnished ->{
                    if( multipleFiltersBinding.selectedFilterView.checkUnfurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    furnishingStatus.remove(filterText)

                }
                R.id.checkSemiFurnished ->{
                    if( multipleFiltersBinding.selectedFilterView.checkSemiFurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    furnishingStatus.remove(filterText)

                }
                R.id.checkUnderConst->{
                    if( multipleFiltersBinding.selectedFilterView.checkUnderConst.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    constructionStatus.remove(filterText)

                }
                R.id.checkReadyToMove->{
                    if( multipleFiltersBinding.selectedFilterView.checkReadyToMove.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    constructionStatus.remove(filterText)

                }
                R.id.checkNewLaunch->{
                    if( multipleFiltersBinding.selectedFilterView.checkNewLaunch.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    constructionStatus.remove(filterText)

                }
                R.id.check_owner->{
                    if( multipleFiltersBinding.selectedFilterView.checkOwner.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    listedBy.remove(filterText)

                }
                R.id.check_dealer->{
                    if( multipleFiltersBinding.selectedFilterView.checkDealer.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    listedBy.remove(filterText)

                }
                R.id.check_builder->{
                if( multipleFiltersBinding.selectedFilterView.checkBuilder.text.equals(filterText))
                    chipGroup.removeView(chipGroup.getChildAt(i))
                    listedBy.remove(filterText)

                }
                R.id.txtBedroomOne ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomOne.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBedrooms.remove(filterText)

                }
                R.id.txtBedroomTwo->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomTwo.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBedrooms.remove(filterText)

                }
                R.id.txtBedroomThree->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomThree.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBedrooms.remove(filterText)


                }
                R.id.txtBedroomFour ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBedroomFour.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBedrooms.remove(filterText)

                }
                R.id.txtBathroomOne ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBathroomOne.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBathRooms.remove(filterText)

                }
                R.id.txtBathroomTwo ->{
                    if( multipleFiltersBinding.selectedFilterView.txtBathroomTwo.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    noOfBathRooms.remove(filterText)

                }

                R.id.txtDatePublished->{
                    if( multipleFiltersBinding.selectedFilterView.txtDatePublished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    sortBy.remove(filterText)

                }
                R.id.txtRelevance ->{
                    if( multipleFiltersBinding.selectedFilterView.txtRelevance.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    sortBy.remove(filterText)

                }
                R.id.txtPriceIncr ->{
                    if( multipleFiltersBinding.selectedFilterView.txtPriceIncr.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    sortBy.remove(filterText)

                }
                R.id.txtPriceDecr ->{
                    if( multipleFiltersBinding.selectedFilterView.txtPriceDecr.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    sortBy.remove(filterText)

                }
                R.id.txtDistance ->{
                    if( multipleFiltersBinding.selectedFilterView.txtDistance.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                    sortBy.remove(filterText)

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
        objFilterData =  ObjFilterData().copy(budget = ObjBudgetLimits(lowerLimit = filterViewModel.lowerLimit.get(),
        upperLimit = filterViewModel.upperLimit.get()), propertyType = propertyType, pricePerSqFt = ObjPricePerSqFtLimits(lowerLimit = filterViewModel.lowerLimitForPerSq.get(), upperLimit = filterViewModel.upperLimitForPerSq.get()),
            noOfBathrooms = noOfBedrooms, noOfBedrooms = noOfBathRooms, furnishingStatus = furnishingStatus, constructionStatus = constructionStatus, listedBy = listedBy, buildUpArea = ObjBuildUpAreaLimits(lowerLimit = filterViewModel.lowerLimitForBuildupArea.get(), upperLimit = filterViewModel.upperLimitForBuildupArea.get()),
            sortBy = sortBy
        )
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
    fun removeCitychip(areaId :String){
        val chipsList = multipleFiltersBinding.cgFilterGroup.childCount

        for(i in 0 until chipsList) {
            val filterText = (chipGroup.getChildAt(i) as Chip).text.toString()
            for (area in filterViewModel.subAreaList.value!!){
                if(areaId.equals(area.areaId) && filterText.equals(area.subArea)) {
                    chipGroup.removeView(chipGroup.getChildAt(i))
                    cityList.remove(filterText)
                    break
                }
            }
            break
        }



    }


}