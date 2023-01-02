package com.vastu.realestate.appModule.dashboard.view.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterTypeClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.appModule.dashboard.viewmodel.FilterViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.databinding.MultipleFiltersBinding
import com.vastu.realestate.registrationcore.model.ObjFilterTypeData
import com.vastu.realestate.registrationcore.model.ObjFilterTypeList
import com.vastu.realestate.registrationcore.model.ObjManageFilterVisibility

class SortAndFilterScreen:Fragment() , IFilterTypeClickListener, IFilterViewHandler {
    lateinit var multipleFiltersBinding: MultipleFiltersBinding
    lateinit var filterViewModel: FilterViewModel
    lateinit var itemsList : ArrayList<ObjFilterTypeList>
    lateinit var customAdapter: FilterTypeAdapter
    lateinit var chipGroup :ChipGroup
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
//        viewPager.isUserInputEnabled = false
//        val adapter = LoginPagerAdapter(parentFragmentManager, lifecycle)
//        viewPager.adapter = adapter
//        val dashboardTab = arrayOf(
//            "Login",
//            "Sign Up"
//        )
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = dashboardTab[position]
//        }.attach()

        val recyclerView: RecyclerView = multipleFiltersBinding.filterType
        itemsList =  prepareItems()
        customAdapter = FilterTypeAdapter(requireContext(),itemsList,this)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        recyclerView.adapter = customAdapter
    }
    private fun prepareItems(): ArrayList<ObjFilterTypeList> {
        val filterTypeList = Gson().fromJson(requireContext().let { BaseUtils.getFilterTypeList(it) }.toString(), ObjFilterTypeData::class.java)
            return filterTypeList.objFilterTypeList
        }

    override fun onFilterItemClickListener(selectedFilterType: String) {
        Log.d("selected",selectedFilterType)

        when(selectedFilterType){
            resources.getString(R.string.by_budget)->
            {
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = true, title = resources.getString(R.string.choose_a_range_below))

            }
            resources.getString(R.string.by_property_type)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false,isVisiblePropertyLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_price_per_sq_ft)->{
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisiblePricePerSqFtLayout = true,title = resources.getString(R.string.choose_a_range_below_per_Sq))
            }

            resources.getString(R.string.by_bedrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleByBedroomsLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_bathrooms)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleByBathroomsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_furnishing)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleByFurnishingLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_constr_status)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleConstructionStsLayout = true ,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_listed_by)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleListedLayout = true,title = resources.getString(R.string.choose_from_below_options))

            resources.getString(R.string.by_buildup_area)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleBuildupAreaLayout = true,title = resources.getString(R.string.choose_a_range_below_Sq))

            resources.getString(R.string.change_sort)->
                objManageFilterVisibility = ObjManageFilterVisibility(isVisibleBudgetLayout = false, isVisibleChangeSortLayout = true ,title = resources.getString(R.string.select_from_below_options))


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
    }
//    fun setBudgetFilter(title: String, image: Drawable?, lowerLimit: String, upperLimit: String) {
//        filterViewModel.title.set(title)
//        filterViewModel.range.set(image)
//        filterViewModel.lowerLimit.set(lowerLimit)
//        filterViewModel.upperLimit.set(upperLimit)
//    }

    override fun removeChip(id: Int) {
        val chipsList = multipleFiltersBinding.cgFilterGroup.childCount

        for(i in 0 until chipsList){
            val filterText = (chipGroup.getChildAt(i) as Chip).text.toString()

            when(id){
                R.id.CheckHouses->{
                    if( multipleFiltersBinding.slectedFilterView.CheckHouses.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckApartment->{
                    if( multipleFiltersBinding.slectedFilterView.CheckApartment.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckBuilderFloor->{
                    if( multipleFiltersBinding.slectedFilterView.CheckBuilderFloor.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckFarmHouse->{
                    if( multipleFiltersBinding.slectedFilterView.CheckFarmHouse.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                 R.id.CheckFurnished->{
                    if( multipleFiltersBinding.slectedFilterView.CheckFurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckUnfurnished ->{
                    if( multipleFiltersBinding.slectedFilterView.CheckUnfurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckSemiFurnished ->{
                    if( multipleFiltersBinding.slectedFilterView.CheckSemiFurnished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckUnderConst->{
                    if( multipleFiltersBinding.slectedFilterView.CheckUnderConst.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckReadyToMove->{
                    if( multipleFiltersBinding.slectedFilterView.CheckReadyToMove.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckNewLaunch->{
                    if( multipleFiltersBinding.slectedFilterView.CheckNewLaunch.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckOwner->{
                    if( multipleFiltersBinding.slectedFilterView.CheckOwner.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckDealer->{
                    if( multipleFiltersBinding.slectedFilterView.CheckDealer.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.CheckBuilder->{
                if( multipleFiltersBinding.slectedFilterView.CheckBuilder.text.equals(filterText))
                    chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBedroomOne ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBedroomOne.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBedroomTwo->{
                    if( multipleFiltersBinding.slectedFilterView.txtBedroomTwo.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBedroomThree->{
                    if( multipleFiltersBinding.slectedFilterView.txtBedroomThree.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBedroomFour ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBedroomFour.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBathroomOne ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBathroomOne.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBathroomTwo ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBathroomTwo.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBathroomThree ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBathroomThree.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtBathroomFour ->{
                    if( multipleFiltersBinding.slectedFilterView.txtBathroomFour.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtDatePublished->{
                    if( multipleFiltersBinding.slectedFilterView.txtDatePublished.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtRelevance ->{
                    if( multipleFiltersBinding.slectedFilterView.txtRelevance.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtPriceIncr ->{
                    if( multipleFiltersBinding.slectedFilterView.txtPriceIncr.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtPriceDecr ->{
                    if( multipleFiltersBinding.slectedFilterView.txtPriceDecr.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }
                R.id.txtDistance ->{
                    if( multipleFiltersBinding.slectedFilterView.txtDistance.text.equals(filterText))
                        chipGroup.removeView(chipGroup.getChildAt(i))
                }

                }
        }
    }
}