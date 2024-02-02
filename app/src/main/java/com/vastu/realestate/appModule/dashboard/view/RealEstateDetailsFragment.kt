package com.vastu.realestate.appModule.dashboard.view

import android.app.Dialog
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.Window
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemerse.slider.listener.CarouselListener
import com.aemerse.slider.model.CarouselItem
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.tabs.TabLayout
import com.potyvideo.library.utils.PublicFunctions.Companion.getScreenHeight
import com.potyvideo.library.utils.PublicFunctions.Companion.getScreenWidth
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
import com.vastu.slidercore.model.response.realestatedetails.BrochureSlider
import com.vastu.slidercore.model.response.realestatedetails.BuilderSlider
import com.vastu.slidercore.model.response.realestatedetails.PropertyDetailsResponseSliderMain
import com.vastu.slidercore.model.response.realestatedetails.PropertySlider
import java.util.regex.Pattern


class RealEstateDetailsFragment : BaseFragment(),RelatedPropertyAdapter.OnItemClickListener,IPropertyDetailsListener,IPropertySliderListener,
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
    var isScrollTab = false

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
            if (args.getSerializable(BaseConstant.RELATED_PROPERTY_DETAILS) != null) {
                val property =  args.getSerializable(BaseConstant.RELATED_PROPERTY_DETAILS) as RelatedProperty
                propertyId = property.propertyId
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
        getPropertySlider()
       setupView ()
        scrollViewAdclicklistner()
    }

    private fun scrollViewAdclicklistner() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            realEstateDetailsBinding.scrollview.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                isScrollTab = false
                if(isVisible(realEstateDetailsBinding.txtBuilderProfile!!)){
                    scrollTabLayout(0)
                }else if(isVisible(realEstateDetailsBinding.txtPropertyDetails!!)){
                    scrollTabLayout(1)
                }else if(isVisible(realEstateDetailsBinding.txtAmmenities!!)){
                    scrollTabLayout(2)
                }else if(isVisible(realEstateDetailsBinding.txtDescription!!)){
                    scrollTabLayout(3)
                }else if(isVisible(realEstateDetailsBinding.highlightsTextview!!)){
                    scrollTabLayout(4)
                }else if(isVisible(realEstateDetailsBinding.txtPhotos!!)){
                    scrollTabLayout(5)
                }else if(isVisible(realEstateDetailsBinding.txtBrochure!!)){
                    scrollTabLayout(6)
                }else if(isVisible(realEstateDetailsBinding.txtSimilarProjects!!)){
                    scrollTabLayout(7)
                }
            })
        }
    }

    fun isVisible(view: View?): Boolean {
        if (view == null || !view.isShown) {
            return false
        }
        val actualPosition = Rect()
        view.getGlobalVisibleRect(actualPosition)
        val screen = Rect(0, 0, getScreenWidth(), getScreenHeight())
        return actualPosition.intersect(screen)
    }

    fun scrollTabLayout(i: Int) {
        isScrollTab = true
        realEstateDetailsBinding.tabLayout.getTabAt(i)!!.select()
       /* Handler().postDelayed(
            Runnable {  }, 100
        )*/
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

    fun setupView(){
        realEstateDetailsBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(!isScrollTab) {
                    when (tab!!.position) {
                        0 -> handleScroll(tab.text)
                        1 -> handleScroll(tab.text)
                        2 -> handleScroll(tab.text)
                        3 -> handleScroll(tab.text)
                        4 -> handleScroll(tab.text)
                        5 -> handleScroll(tab.text)
                        6 -> handleScroll(tab.text)
                        7 -> handleScroll(tab.text)
                        8 -> handleScroll(tab.text)


                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun handleScroll(text: CharSequence?) {
        when(text){
            "Builder Profile" -> scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtBuilderProfile)
            "Property Details"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtPropertyDetails)

            "Amenities"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtAmmenities)

            "Description"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtDescription)

            "Highlights"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.highlightsTextview)

            "Property Photo & Floor Plan"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtPhotos)

            "Brochure"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtBrochure)

            "Similar Projects"->scrollToView( realEstateDetailsBinding.scrollview,realEstateDetailsBinding.txtSimilarProjects)


        }

    }

    private fun scrollToView(scrollViewParent: NestedScrollView, view: View) {
        // Get deepChild Offset
        val childOffset = Point()
        getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y)
    }

    private fun getDeepChildOffset(
        mainParent: ViewGroup,
        parent: ViewParent,
        child: View,
        accumulatedOffset: Point
    ) {
        val parentGroup = parent as ViewGroup
        accumulatedOffset.x += child.left
        accumulatedOffset.y += child.top
        if (parentGroup == mainParent) {
            return
        }
        getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
    }


    override fun onClickBuilderProfile() {
        realEstateDetailsBinding.scrollview.smoothScrollTo(0, realEstateDetailsBinding.txtAmmenities.getTop());


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
            var coItem  =CarouselItem()
            if(slider.type.equals("video")){
                coItem = CarouselItem(
                    imageUrl = slider.thumbnail,
                    caption = ""
                )
            }else {
                coItem = CarouselItem(
                    imageUrl = slider.image,
                    caption = ""
                )
            }

            imageListbuilder.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderBuilder.setData(imageListbuilder)
           /// imageSliderBuilder.autoPlayDelay =1000
            //imageSliderBuilder.setIndicator(custom)
           // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderBuilder.carouselListener = object : CarouselListener {
          override fun onClick(position: Int, carouselItem: CarouselItem) {
              var selectedPostition = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.builder_slider[position]
              if(selectedPostition.type =="video"){
                  createDialogDashboard( propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.builder_slider[position].image)
              }else{
                  createImageDialog(imageListbuilder.get(position).imageUrl!!)
              }

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }

        getPropertyDetails()
        sliderDataForFloorPlan(propertySliderResponseMain)
        sliderDataForBrochure(propertySliderResponseMain)
       // setPhotosPropertyDetails(propertySliderResponseMain.getPropertySliderImagesResponse.propertySliderImages)
    }

    fun createDialogDashboard(link: String) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_video_dialog)
        val videoView = dialog.findViewById<com.potyvideo.library.AndExoPlayerView>(R.id.andExoPlayerViewType)
        val closeImageView = dialog.findViewById<ImageView>(R.id.img_cross)
        videoView.setSource(link)
        closeImageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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
            val relatedPropertyAdapter = RelatedPropertyAdapter(this,requireContext(),relatedProperty)
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
        checkBuilderOROwner(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0))
        realEstateDetailsBinding.apply {
            val property = propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0)
            propertyIdDataList = property
            propertyData = property
            val htmlPattern = "<[^>]*>"
            highlightsTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights.trim(),Html.FROM_HTML_MODE_LEGACY));
            descriptionTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).description,Html.FROM_HTML_MODE_LEGACY));
            facingTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).flatFacing))
            overViewTextview.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).builderOverview))
            setAmenityDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.amenities)
            setRelatedPropertyDetails(propertyDataResponseMain.getPropertyIdDetailsResponse.relatedProperty)
        }
    }

    private fun checkBuilderOROwner(propertyData: PropertyIdData) {
       if(propertyData.isBuilder){
           //realEstateDetailsBinding.tab1.text = "".toString()
       }else{

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
            var coItem  =CarouselItem()
            if(slider.type.equals("video")){
                coItem = CarouselItem(
                    imageUrl = slider.thumbnail,
                    caption = ""
                )
            }else {
                coItem = CarouselItem(
                    imageUrl = slider.image,
                    caption = ""
                )
            }
            imageListCarouselProperty.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyPlan.setData(imageListCarouselProperty)
           // imageSliderPropertyPlan.autoPlayDelay =1000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyPlan.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {

                var selectedPostition = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.property_slider[position]
                if(selectedPostition.type =="video"){
                    createDialogDashboard( propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.property_slider[position].image)
                }else{
                    createImageDialog(imageListCarouselProperty.get(position).imageUrl!!)
                }


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
            var coItem  =CarouselItem()
            if(slider.type.equals("video")){
                coItem = CarouselItem(
                    imageUrl = slider.thumbnail,
                    caption = ""
                )
            }else {
                coItem = CarouselItem(
                    imageUrl = slider.image,
                    caption = ""
                )
            }
            imageListbrocure.add(coItem)
        }
        realEstateDetailsBinding.apply {
            imageSliderPropertyBrochure.setData(imageListbrocure)
            //imageSliderPropertyBrochure.autoPlayDelay =1000

            // imageSlider.setIndicator(custom)
        }



        realEstateDetailsBinding.imageSliderPropertyBrochure.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                var selectedPostition = propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.brochure_slider[position]
                if(selectedPostition.type =="video"){
                    createDialogDashboard( propertySliderResponseMain.GetPropertySliderImagesResponse.PropertySliderImages.brochure_slider[position].image)
                }else{
                    createImageDialog(imageListbrocure.get(position).imageUrl!!)
                }

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }
    }

    override fun onItemClick(propertyData: RelatedProperty) {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.RELATED_PROPERTY_DETAILS, propertyData)
        findNavController().navigate(
            R.id.RealEstateDetailsFragment,
            bundle
        )
    }
}
