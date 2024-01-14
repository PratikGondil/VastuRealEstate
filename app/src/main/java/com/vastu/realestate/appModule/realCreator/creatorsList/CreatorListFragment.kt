package com.vastu.realestate.appModule.realCreator.creatorsList

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.networkService.util.Constants
import com.vastu.realCreator.realCreatorList.model.ObjRealCreatorListRes
import com.vastu.realCreator.realCreatorList.model.RealCreatorDatum
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realCreator.realCreatorSearch.model.ProfileDaum
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.CreatorListAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.realCreator.infoPage.ObjSelectedProfile
import com.vastu.realestate.databinding.CreatorlistFragmentBinding
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.request.ObjFilterData
import com.vastu.realestatecore.model.response.*
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse

class CreatorListFragment : BaseFragment(), IToolbarListener,
    CreatorListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
    ICreatorListListener {
    private lateinit var realEstateBinding: CreatorlistFragmentBinding
    private lateinit var realEstateViewModel: CreatorListViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private val imageList = ArrayList<SlideModel>()
    lateinit var objFilterData: ObjFilterData
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FragmentContainerView>
    lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment
    lateinit var realEstatListUpdated: List<RealCreatorDatum>
    lateinit var objSelectedProfile: ObjSelectedProfile

    var realEstateAdapter: CreatorListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateViewModel = ViewModelProvider(this)[CreatorListViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateBinding =
            DataBindingUtil.inflate(inflater, R.layout.creatorlist_fragment, container, false)
        realEstateBinding.lifecycleOwner = this
        realEstateBinding.creatorListViewModel = realEstateViewModel
        realEstateBinding.drawerViewModel = drawerViewModel
        realEstateViewModel.iCreatorListListener = this
        drawerViewModel.iToolbarListener = this
        if(arguments!=null){
            objSelectedProfile = requireArguments().getSerializable("profile") as ObjSelectedProfile
        }
        getRealEstateList()

        return realEstateBinding.root
    }

    private fun setSliderData() {
        try {
            getRealEstateList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSuccessGetRealCreatorList(objRealCreatorListRes: ObjRealCreatorListRes) {
        try {
            val realEstates = objRealCreatorListRes.getRealCreatorDetailsResponse.realCreatorData
            //   val sliderData=objGetPropertyListResMain.getPropertyDetailsResponse.adSlider
            realEstateBinding.apply {
                if (realEstates.isNotEmpty()) {
                    searchFilterLayout.visibility = View.VISIBLE
                    rvCreatorList.visibility = View.VISIBLE
                    stopShimmerAnimation()
                    getRealEstateDetails(realEstates)
                } else {
                    searchFilterLayout.visibility = View.GONE
                    rvCreatorList.visibility = View.GONE
                    stopShimmerAnimation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureGetRealCreatorList(objRealCreatorListRes: ObjRealCreatorListRes) {

    }


    override fun searchFilter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<PropertyData> = ArrayList()

        // running a for loop to compare elements.
//        for (item in realEstatListUpdated) {
//            if (item.address?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.area?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.bookingAmount?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.city?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.price?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.sellType?.toLowerCase()?.contains(text.toLowerCase()) == true
//                || item.propertyTitle?.toLowerCase()?.contains(text.toLowerCase())==true
//            ) {
//                filteredlist.add(item)
//            }
//        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            // Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
         //   realEstateAdapter?.filterList(filteredlist)
        }
    }


    private fun refreshAPI() {
        Handler().postDelayed({
            onRefresh()
        }, 550)
    }


    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getRealEstateList() {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        if (activity is DashboardActivity) {
            (activity as DashboardActivity).bottomNavigationView.visibility = View.VISIBLE
        }
        try {
            realEstateBinding.loadingLayout.startShimmerAnimation()
            DashboardFragment.userId?.let {
                if (language != null) {
                    realEstateViewModel.getRealCreatorList(
                        objSelectedProfile.profile!!,
                        ApiUrlEndPoints.GET_REAL_CREATOR,
                        language,
                        taluka = objSelectedProfile.taluka!!,
                        subarea = objSelectedProfile.subArea!!
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getRealEstateDetails(realEstate: List<RealCreatorDatum>) {
        try {
            realEstatListUpdated = realEstate
            val recyclerViewRealEstate = realEstateBinding.rvCreatorList
            //val realEstates = RealEstateList.getRealEstateData(requireContext())
            realEstateAdapter = CreatorListAdapter(this, realEstate)

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




    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("", false, true)
    }


    override fun onItemClick(realCreatorDatum: RealCreatorDatum) {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_DETAILS, realCreatorDatum)
        bundle.putSerializable("profile",objSelectedProfile)
        findNavController().navigate(
            R.id.action_creatorListFragment_to_creatorDetailsFragment,
            bundle
        )
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


    fun onErrorResponse(message: String, isSuccess: Boolean, isNetworkFailure: Boolean) {
        showDialog(message, isSuccess, isNetworkFailure)
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }


}