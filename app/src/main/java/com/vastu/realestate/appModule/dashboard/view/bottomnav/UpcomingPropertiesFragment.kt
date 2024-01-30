package com.vastu.realestate.appModule.dashboard.view.bottomnav

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil

import androidx.fragment.app.FragmentContainerView

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IRealEstateListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.view.filter.SortAndFilterScreenUpcoming
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.databinding.FragmentRealEstateBinding
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.request.ObjFilterData
import com.vastu.realestatecore.model.response.*
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse


class UpcomingPropertiesFragment : BaseFragment(), IRealEstateListener, IToolbarListener,
    RealEstateAdapter.OnItemClickListener, OnRefreshListener,
    IFilterClickListener {
    private lateinit var realEstateBinding: FragmentRealEstateBinding
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private val imageList = ArrayList<SlideModel>()
    lateinit var objFilterData: ObjFilterData
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FragmentContainerView>
    lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment
    lateinit var realEstatListUpdated: List<PropertyData>
    var realEstateAdapter: RealEstateAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateViewModel = ViewModelProvider(this)[RealEstateViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_real_estate, container, false)
        realEstateBinding.lifecycleOwner = this
        realEstateBinding.realEstateViewModel = realEstateViewModel
        realEstateBinding.drawerViewModel = drawerViewModel
        realEstateViewModel.iRealEstateListener = this
        drawerViewModel.iToolbarListener = this
        realEstateViewModel.iFilterClickListener = this
        getRealEstateList()
       // setSliderData()
//        realEstateBinding.swipeContainer.setOnRefreshListener(this)
//        realEstateBinding.swipeContainer.setColorSchemeResources(R.color.button_color)
//        bottomSheetBehavior =BottomSheetBehavior.from(realEstateBinding.filterFragment)
        return realEstateBinding.root
    }

    private fun setSliderData() {
        try {
//            imageList.clear()
//            getAdvertisementSlider =
//                PreferenceManger.getAdvertisementSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
//            realEstateBinding.apply {
//                for (slider in getAdvertisementSlider.advertiseData) {
//                    imageList.add(SlideModel(slider.adSlider))
//                }
//                imageSlider.setImageList(imageList, ScaleTypes.FIT)
//                imageSlider.startSliding(3000)
//            }
            getRealEstateList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun searchFilter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<PropertyData> = ArrayList()

        // running a for loop to compare elements.
        for (item in realEstatListUpdated) {
            if (item.address?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.area?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.bookingAmount?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.city?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.price?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.sellType?.toLowerCase()?.contains(text.toLowerCase()) == true
                || item.propertyTitle?.toLowerCase()?.contains(text.toLowerCase())==true
            ) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            realEstateAdapter?.filterList(filteredlist)
        }
    }

    override fun onSuccessAddWishList(addWishlistResponse: AddWishlistResponse) {
        hideProgressDialog()
        showDialog(addWishlistResponse.registerResponse.responseStatusHeader.statusDescription,true,false)
        refreshAPI()

    }

    override fun onFailureAddWishList(addWishlistResponse: AddWishlistResponse) {
        hideProgressDialog()
        showDialog(addWishlistResponse.registerResponse.responseStatusHeader.statusDescription,false,false)

    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getRealEstateList() {
        try {
            realEstateBinding.loadingLayout.startShimmerAnimation()
            userId?.let { realEstateViewModel.getPropertyList(it,ApiUrlEndPoints.GET_UPCOMING_NEW) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSuccessGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        try {
            val realEstates = objGetPropertyListResMain.getPropertyDetailsResponse.propertyData
            realEstateBinding.apply {
                if (realEstates.isNotEmpty()) {
                    searchFilterLayout.visibility = View.VISIBLE
                    rvRealEstste.visibility = View.VISIBLE
                    stopShimmerAnimation()
                    getRealEstateDetails(realEstates)
                } else {
                    searchFilterLayout.visibility = View.GONE
                    rvRealEstste.visibility = View.GONE
                    stopShimmerAnimation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRealEstateDetails(realEstate: List<PropertyData>) {
        try {
            realEstatListUpdated = realEstate
            val recyclerViewRealEstate = realEstateBinding.rvRealEstste
            //val realEstates = RealEstateList.getRealEstateData(requireContext())
            realEstateAdapter = RealEstateAdapter(this, realEstate)
            recyclerViewRealEstate.adapter = realEstateAdapter
            recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopShimmerAnimation() {
        try {
            realEstateBinding.apply {
                loadingLayout.stopShimmerAnimation()
                loadingLayout.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureGetRealEstateList(objGetPropertyListResMain: ObjGetPropertyListResMain) {
        stopShimmerAnimation()
        showDialog(
            objGetPropertyListResMain.propertyResponse.responseStatusHeader.statusDescription!!,
            false,
            false
        )
    }

    override fun onFilterPropertyListSuccess(objGetFilterDataResponse: ObjGetFilterDataResponse) {
        getRealEstateDetails(objGetFilterDataResponse.filteredPropertyResponse)
    }

    override fun onFilterPropertyListFailure(objFilterDataResponseMain: ObjFilterDataResponseMain) {
        showDialog(
            objFilterDataResponseMain.objfilterDataResponse.responseStatusHeader.statusDescription!!,
            false,
            false
        )

    }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("", false, true)
    }


    override fun onItemClick(propertyData: PropertyData) {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_DETAILS, propertyData)
        findNavController().navigate(
            R.id.action_RealEstateFragment_to_RealEstateDetailsFragment,
            bundle
        )
    }

    override fun onWishlistClick(propertyData: PropertyData) {
        showProgressDialog()
        userId?.let {
            propertyData.propertyId?.let { it1 ->
                realEstateViewModel.getAddToWishlist(it,it1)
            }
        }
    }

    private fun refreshAPI() {
        Handler().postDelayed({
            onRefresh()
        }, 550)
    }

    override fun onClickBack() {
        try {
            activity?.onBackPressed()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClickMenu() {
        //onClickMenu
    }

    override fun onClickNotification() {
        //onClickNotifications
    }

    override fun onRefresh() {
        realEstateBinding.swipeContainer.isRefreshing = false
        getRealEstateList()
    }

    override fun setFilterView() {
        try {
            val modalbottomSheetFragment = SortAndFilterScreenUpcoming(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar
            )
            modalbottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                modalbottomSheetFragment.tag
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun applyFilters(objFilterData: ObjFilterData) {
//
        realEstateViewModel.budgetLimit.add(realEstateViewModel.lowerLimit.get()!!)
        realEstateViewModel.budgetLimit.add(realEstateViewModel.upperLimit.get()!!)
//        realEstateViewModel.budgetLimit.add("25,00,000")
//        realEstateViewModel.budgetLimit.add("1,00,00,000")

        realEstateViewModel.buildUpAreaLimits.add(realEstateViewModel.lowerLimitForBuildupArea.get()!!)
        realEstateViewModel.buildUpAreaLimits.add(realEstateViewModel.upperLimitForBuildupArea.get()!!)
//        realEstateViewModel.buildUpAreaLimits.add("1200")
//        realEstateViewModel.buildUpAreaLimits.add("1600")

        /*Dummy Response for testing*/
//        realEstateViewModel.cityList.add("6")
//        realEstateViewModel.propertyType.add("Apartments")
//        realEstateViewModel.propertyType.add("Houses & Villas")
//        realEstateViewModel.noOfBedrooms.add("1")
//        realEstateViewModel.noOfBedrooms.add("2")
//        realEstateViewModel.noOfBedrooms.add("3")
//        realEstateViewModel.noOfBathRooms.add("1")
//        realEstateViewModel.noOfBathRooms.add("2")
//        realEstateViewModel.noOfBathRooms.add("3")
//        realEstateViewModel.listedBy.add("Sell")
//        realEstateViewModel.sortBy.add("Price:Low to High")
//        this.objFilterData =  ObjFilterData().copy(subAreaId =realEstateViewModel.cityList, budget = realEstateViewModel.budgetLimit, propertyType = realEstateViewModel.propertyType,
//            noOfBathrooms = realEstateViewModel.noOfBathRooms, noOfBedrooms = realEstateViewModel.noOfBedrooms, listedBy = realEstateViewModel.listedBy, buildUpArea = realEstateViewModel.buildUpAreaLimits,
//            sortBy = realEstateViewModel.sortBy
//        )
        PreferenceManger.put(objFilterData, PreferenceKEYS.FILTERDATA)
        realEstateViewModel.callApplyFilterApi(objFilterData)
    }

    fun clearFilter() {
//    objFilterData = ObjFilterData()
        PreferenceManger.put(null, PreferenceKEYS.FILTERDATA)

        realEstateViewModel.budgetLimit = arrayListOf()
        realEstateViewModel.buildUpAreaLimits = arrayListOf()
        realEstateViewModel.selectedAreaList = arrayListOf()
        realEstateViewModel.propertyType = arrayListOf()
        realEstateViewModel.noOfBedrooms = arrayListOf()
        realEstateViewModel.noOfBathRooms = arrayListOf()
        realEstateViewModel.listedBy = arrayListOf()
        realEstateViewModel.sortBy = arrayListOf()

        getRealEstateList()
    }

    fun onErrorResponse(message: String, isSuccess: Boolean, isNetworkFailure: Boolean) {
        showDialog(message, isSuccess, isNetworkFailure)
    }


}