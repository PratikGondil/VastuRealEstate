package com.vastu.realestate.appModule.dashboard.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemerse.slider.listener.CarouselListener
import com.aemerse.slider.model.CarouselItem
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.propertycore.model.response.AddWishlistResponse
import com.vastu.propertycore.model.response.Amenity
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.propertycore.model.response.PropertyIdData
import com.vastu.propertycore.model.response.RelatedProperty
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.AmenitiesAdapter
import com.vastu.realestate.appModule.dashboard.adapter.PropertyPhotoPlanAdapter
import com.vastu.realestate.appModule.dashboard.adapter.RelatedPropertyAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.databinding.FragmentRealEstateDetailsBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.property.PropertySliderImage
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.slidercore.model.response.realestatedetails.BrochureSlider
import com.vastu.slidercore.model.response.realestatedetails.BuilderSlider
import com.vastu.slidercore.model.response.realestatedetails.PropertyDetailsResponseSliderMain
import com.vastu.slidercore.model.response.realestatedetails.PropertySlider
import java.util.regex.Pattern

class RealEstateDetailsFragment : BaseFragment(),IPropertyDetailsListener,IPropertySliderListener,
    IToolbarListener {
    private lateinit var realEstateDetailsBinding: FragmentRealEstateDetailsBinding
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
    private var propertyId : String? = null
    private var propertySliderList : List<PropertySlider>? = null
    private var buiderSlider : List<BuilderSlider>? = null
    private var brouchureSlider : List<BrochureSlider>? = null

    private val imageList = ArrayList<SlideModel>()
    private val imageListbuilder = ArrayList<CarouselItem>()
    private val imageListCarouselProperty = ArrayList<CarouselItem>()
    private val imageListbrocure = ArrayList<CarouselItem>()

    private lateinit var drawerViewModel: DrawerViewModel
    private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")
    lateinit var  propertyIdDataList: PropertyIdData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        realEstateDetailsViewModel = ViewModelProvider(this)[RealEstateDetailsViewModel::class.java]
        realEstateDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate_details, container, false)
        realEstateDetailsBinding.realEstateDetailsViewModel = realEstateDetailsViewModel
        realEstateDetailsBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        realEstateDetailsViewModel.iPropertyDetailsListener = this
        realEstateDetailsViewModel.iPropertySliderListener = this
        realEstateDetailsBinding.drawerViewModel = drawerViewModel
        getBundleData()
        return realEstateDetailsBinding.root
    }
    private fun getBundleData(){
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.PROPERTY_DETAILS) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_DETAILS) as PropertyData
                propertyId = property.propertyId
            }
            if (args.getSerializable(BaseConstant.PROPERTY_ID) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_ID).toString()
                propertyId = property
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
        getPropertySlider()
    }
    private fun getPropertySlider(){
        showProgressDialog()
        propertyId?.let { realEstateDetailsViewModel.getPropertySlider(it) }
    }

    override fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        showDialog(propertyDataResponseMain.propertyIdResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onSuccessAddWishList(addWishlistResponse: AddWishlistResponse) {
        hideProgressDialog()
        showDialog(addWishlistResponse.registerResponse.responseStatusHeader.statusDescription,true,false)
        Log.e("********SSresponse",addWishlistResponse.toString())
    }

    override fun onFailureAddWishList(addWishlistResponse: AddWishlistResponse) {
        hideProgressDialog()
        showDialog(addWishlistResponse.registerResponse.responseStatusHeader.statusDescription,false,true)
        Log.e("*********ffresponse",addWishlistResponse.toString())
    }

    override fun addToWishlist() {
        hideProgressDialog()
        userId?.let {
            propertyId?.let { it1 ->
                realEstateDetailsViewModel.getAddToWishlist(it,it1)
            }
        }
    }

    override fun onClickBuilderProfile() {

//        realEstateDetailsBinding.tabLayout.setOnClickListener{
//            when(id){
//                R.id.tab1->
//                    realEstateDetailsBinding.scrollview.scrollTo(5,10)
//            }
//        }
//        val tabLayout = requireView().findViewById<TabLayout>(R.id.tabLayout)
//        val viewPager = requireView().findViewById<ViewPager>(R.id.viewPager)
//
//        // Create an instance of your ViewPager adapter (assuming you have one)
//        val adapter = MyPagerAdapter(supportFragmentManager)
//
//        // Set the adapter to the ViewPager
//        viewPager.adapter = adapter
//
//        // Connect the TabLayout with the ViewPager
//        tabLayout.setupWithViewPager(viewPager)
//
//        // Add an OnTabSelectedListener to listen for tab selection events
//        realEstateDetailsBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                // Handle tab selection
//                tab?.let {
//                    when(id){
//                        R.id.tab1->
//                            realEstateDetailsBinding.scrollview.scrollTo(5,10)
//                    }
////                    viewPager.currentItem = it.position
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                // Handle tab unselection
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // Handle tab reselection
//            }
//        })
    }





    override fun onSuccessPropertySliderById(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
        setCustomIndicator()
        imageListbuilder.clear()
        hideProgressDialog()
        buiderSlider = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.builder_slider
        for( slider in buiderSlider!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListbuilder.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderBuilder.setData(imageListbuilder)
            imageSliderBuilder.autoPlayDelay =3000
            //imageSliderBuilder.setIndicator(custom)
           // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderBuilder.carouselListener = object : CarouselListener {
          override fun onClick(position: Int, carouselItem: CarouselItem) {
             createImageDialog(imageListbuilder.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }

        getPropertyDetails()
        sliderDataForFloorPlan(propertySliderResponseMain)
        sliderDataForBrochure(propertySliderResponseMain)
       // setPhotosPropertyDetails(propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages)
    }

    private fun setCustomIndicator() {

    }

    override fun onFailurePropertySliderById(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
        hideProgressDialog()
        getPropertyDetails()
    }

    private fun getPropertyDetails(){
        hideProgressDialog()
        userId?.let {
            propertyId?.let { it1 ->
                realEstateDetailsViewModel.getPropertyDetails(it,it1)
            }
        }
    }

    override fun addPropertyEnquiry() {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.PROPERTY_ID, propertyId)
        findNavController().navigate(R.id.action_RealEstateDetailsFragment_to_AddPropertyEnquiryFragment,bundle)
    }
    private fun setAmenityDetails(amenity:List<Amenity>){
        try {
            val recyclerViewAmenity = realEstateDetailsBinding.aminityRecyclerView
            val amenityAdapter = AmenitiesAdapter(requireContext(),amenity)
            recyclerViewAmenity.adapter = amenityAdapter
            recyclerViewAmenity.layoutManager = GridLayoutManager(activity,2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun setRelatedPropertyDetails(relatedProperty:List<RelatedProperty>){
        try {
            val recyclerViewProperty = realEstateDetailsBinding.relatedRecyclerView
            val relatedPropertyAdapter = RelatedPropertyAdapter(requireContext(),relatedProperty)
            recyclerViewProperty.adapter = relatedPropertyAdapter
            recyclerViewProperty.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setPhotosPropertyDetails(propertySliderImage:List<PropertySliderImage>){
        try {
            val recyclerPhotoProperty = realEstateDetailsBinding.imgRecyclerView
            val propertyPhotoAdapter = PropertyPhotoPlanAdapter(requireContext(),propertySliderImage)
            recyclerPhotoProperty.adapter = propertyPhotoAdapter
            recyclerPhotoProperty.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun chatEnquiry() {


    }

    override fun viewbroture() {
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        realEstateDetailsBinding.apply {
            val property = propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0)
            propertyIdDataList = property
            propertyData = property
            val htmlPattern = "<[^>]*>"
            highlightsTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights.trim(),Html.FROM_HTML_MODE_LEGACY));
            descriptionTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).description,Html.FROM_HTML_MODE_LEGACY));

            setAmenityDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.amenities)
            setRelatedPropertyDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.relatedProperty)
        }
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    override fun onClickBack() {
       activity?.onBackPressed()
    }

    override fun onClickMenu() {
    }

    override fun onClickNotification() {
    }
    override fun onPause() {
        super.onPause()
    }
    fun sliderDataForFloorPlan(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
        imageListCarouselProperty.clear()
        hideProgressDialog()
        propertySliderList = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.property_slider
        for( slider in propertySliderList!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListCarouselProperty.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyPlan.setData(imageListCarouselProperty)
            imageSliderPropertyPlan.autoPlayDelay =3000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyPlan.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                createImageDialog(imageListCarouselProperty.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }
    }
    fun sliderDataForBrochure(propertySliderResponseMain: PropertyDetailsResponseSliderMain) {
        imageListbrocure.clear()
        hideProgressDialog()
        brouchureSlider = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.brochure_slider
        for( slider in brouchureSlider!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListbrocure.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyBrochure.setData(imageListbrocure)
            imageSliderPropertyBrochure.autoPlayDelay =3000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyBrochure.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                createImageDialog(imageListbrocure.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }
    }
}
