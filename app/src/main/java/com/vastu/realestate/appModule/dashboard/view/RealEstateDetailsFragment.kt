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
import java.util.regex.Pattern

class RealEstateDetailsFragment : BaseFragment(),IPropertyDetailsListener,IPropertySliderListener,
    IToolbarListener {
    private lateinit var realEstateDetailsBinding: FragmentRealEstateDetailsBinding
    private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
    private var propertyId : String? = null
    private var sliderList : List<PropertySliderImage>? = null
    private val imageList = ArrayList<SlideModel>()
    private val imageListCarousel = ArrayList<CarouselItem>()
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

    override fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        imageListCarousel.clear()
        hideProgressDialog()
        sliderList = propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages
        for( slider in sliderList!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListCarousel.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSlider.setData(imageListCarousel)
            imageSlider.autoPlayDelay =3000

           // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSlider.carouselListener = object : CarouselListener {
          override fun onClick(position: Int, carouselItem: CarouselItem) {
             createImageDialog(imageListCarousel.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }

        getPropertyDetails()
        sliderDataForFloorPlan(propertySliderResponseMain)
        sliderDataForBrochure(propertySliderResponseMain)
        setPhotosPropertyDetails(propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages)
    }

    override fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
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
    fun sliderDataForFloorPlan(propertySliderResponseMain: PropertySliderResponseMain) {
        //image_slider_property_plan

        imageListCarousel.clear()
        hideProgressDialog()
        sliderList = propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages
        for( slider in sliderList!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListCarousel.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyPlan.setData(imageListCarousel)
            imageSliderPropertyPlan.autoPlayDelay =3000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyPlan.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                createImageDialog(imageListCarousel.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }
    }
    fun sliderDataForBrochure(propertySliderResponseMain: PropertySliderResponseMain) {
        //image_slider_property_plan

        imageListCarousel.clear()
        hideProgressDialog()
        sliderList = propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages
        for( slider in sliderList!!){
            var coItem  =CarouselItem(
                imageUrl = slider.image,
                caption = ""
            )
            imageListCarousel.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyBrochure.setData(imageListCarousel)
            imageSliderPropertyBrochure.autoPlayDelay =3000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyBrochure.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                createImageDialog(imageListCarousel.get(position).imageUrl!!)

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }
    }
}
