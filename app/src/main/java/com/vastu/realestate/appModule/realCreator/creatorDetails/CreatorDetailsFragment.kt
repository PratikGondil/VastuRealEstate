package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aemerse.slider.listener.CarouselListener
import com.aemerse.slider.model.CarouselItem
import com.vastu.networkService.util.Constants
import com.vastu.propertycore.model.response.PropertyIdData
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.creatorDetails.model.SingalRealCreatorDatum
import com.vastu.realCreator.creatorDetails.model.Slider
import com.vastu.realCreator.rate_us.model.ObjCreatorRateUsRes
import com.vastu.realCreator.rate_us.repository.CreatorRateUsRepository
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.realCreator.infoPage.ObjSelectedProfile
import com.vastu.realestate.databinding.CreatorDetailsPageBinding
import com.vastu.realestate.utils.ApiUrlEndPoints
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.CommonUtils
import com.vastu.realestate.utils.PreferenceManger
import java.util.regex.Pattern

class CreatorDetailsFragment : BaseFragment(), IToolbarListener, ICreatorDetailsListener {


    private lateinit var creatorDetailsPageBinding: CreatorDetailsPageBinding
    private lateinit var creatorDetailsViewModel: CreatorDetailsViewModel
    private var propertyId: String? = null


    private lateinit var drawerViewModel: DrawerViewModel
    private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")
    lateinit var propertyIdDataList: PropertyIdData
    lateinit var selectedProfile: ObjSelectedProfile
    lateinit var  property :SingalRealCreatorDatum
    lateinit var sliderData: List<Slider>
    private val imageListCarouselProperty = ArrayList<CarouselItem>()
    var selectedPosition :String =""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        creatorDetailsViewModel = ViewModelProvider(this)[CreatorDetailsViewModel::class.java]
        creatorDetailsPageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.creator_details_page, container, false
        )
        creatorDetailsPageBinding.creatorListDetailsViewModel = creatorDetailsViewModel
        creatorDetailsPageBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        creatorDetailsViewModel.iCreatorDetailsListener = this

        creatorDetailsPageBinding.drawerViewModel = drawerViewModel
        getBundleData()
        return creatorDetailsPageBinding.root
    }

    fun callPhoneNumber(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:"+property.mobile)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_creator))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getBundleData() {
        if (activity is DashboardActivity) {
            (activity as DashboardActivity).bottomNavigationView.visibility = View.GONE
        }
        val args = this.arguments
        if (args != null) {
           if (args.getString(BaseConstant.SELECTED_REAL_CREATOR_POSITION) != null) {
               selectedPosition = args.getString(BaseConstant.SELECTED_REAL_CREATOR_POSITION)!!
            }
            if (args.getSerializable("profile") != null) {
                selectedProfile =
                    requireArguments().getSerializable("profile") as ObjSelectedProfile

            }

        }
        creatorDetailsAPICall(selectedPosition)
    }



    override fun onClickBack() {
        val bundle = Bundle()
        bundle.putSerializable("profile", selectedProfile)
        findNavController().navigate(
            R.id.action_creatorDetailsFragment_to_creatorListFragment,
            bundle
        )
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onWhatsAppClick() {
        val phoneNumber = property.mobile
        val message = "Hello, this is a WhatsApp message from my app!"

        val whatsappIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
        whatsappIntent.`package` = "com.whatsapp"
        whatsappIntent.putExtra("sms_body", message)

        try {
            startActivity(whatsappIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
        }
    }

    override fun onShareClick() {
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share To:"))
    }

    override fun onEmailClick() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(property.email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No email app installed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCallClick() {
        val phoneNumber = property.mobile// Replace with the phone number you want to call

        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

        try {
            startActivity(callIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Calling is not supported on this device.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSuccessGetRateUs(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes) {
        showDialog(objcreatorRateUsRes.rateUsResponse.responseStatusHeader.statusDescription,true,false)
        Handler().postDelayed({
            hideDialog()
        }, 750)
    }

    override fun onFailureGetRateUs(objcreatorRateUsRes: ObjCreatorRateUsRes.CreatorRateUsRes) {
        showDialog(objcreatorRateUsRes.rateUsResponse.responseStatusHeader.statusDescription,false,false)

    }

    override fun onRateUsClick() {
        creatorDetailsViewModel.callRateUsAPI(DashboardFragment.userId.toString(),creatorDetailsPageBinding.smallRating.rating.toString(),property.realCreatorID)

    }

    override fun onSuccessGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        property = objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.singalRealCreatorData.get(0)
        sliderData =objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.slider

        for( slider in sliderData!!){
            var coItem  =CarouselItem()
            if(slider.video){
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
        creatorDetailsPageBinding.apply {
            imageSliderBuilder.setData(imageListCarouselProperty)
            /// imageSliderBuilder.autoPlayDelay =1000
            //imageSliderBuilder.setIndicator(custom)
            // imageSlider.setIndicator(custom)
        }



        creatorDetailsPageBinding.imageSliderBuilder.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                var selectedPostition = objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.slider[position]
                if(selectedPostition.video){
                    createDialogDashboard( objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.slider[position].image)
                }else{
                    createImageDialog( objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.slider[position].image)
                }

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }

        setView()
    }

    override fun onFailureGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        showDialog(
            objDetailsCreatorRes.singalRealCreatorResponse.responseStatusHeader.statusDescription,
            false,
            false
        )
    }
    fun creatorDetailsAPICall(selectedPosition: String) {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        language?.let { creatorDetailsViewModel.creatorDetailsAPICall(it, selectedPosition) }
    }


    fun setView(){
        creatorDetailsPageBinding.name.text=property.name
        creatorDetailsPageBinding.address.text= property.address
        creatorDetailsPageBinding.rating.text=property.rating+requireContext().getString(R.string.star)
        creatorDetailsPageBinding.nameEmp.text = property.profileName
        creatorDetailsPageBinding.mobileNumber.text=property.mobile
        creatorDetailsPageBinding.txtInfo.text=property.overview

        creatorDetailsPageBinding.mobileNumber.setOnClickListener {
            callPhoneNumber()

        }


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




}