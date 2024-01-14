package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aemerse.slider.model.CarouselItem
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.networkService.util.Constants
import com.vastu.propertycore.model.response.PropertyIdData
import com.vastu.realCreator.creatorDetails.model.ObjDetailsCreatorRes
import com.vastu.realCreator.realCreatorList.model.RealCreatorDatum
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.realCreator.infoPage.ObjSelectedProfile
import com.vastu.realestate.databinding.CreatorDetailsPageBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.response.PropertyData
import com.vastu.slidercore.model.response.realestatedetails.BrochureSlider
import com.vastu.slidercore.model.response.realestatedetails.BuilderSlider
import com.vastu.slidercore.model.response.realestatedetails.PropertySlider
import java.util.regex.Pattern

class CreatorDetailsFragment: BaseFragment(), IToolbarListener,ICreatorDetailsListener {



    private lateinit var creatorDetailsPageBinding: CreatorDetailsPageBinding
    private lateinit var creatorDetailsViewModel: CreatorDetailsViewModel
    private var propertyId : String? = null


    private lateinit var drawerViewModel: DrawerViewModel
    private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")
    lateinit var  propertyIdDataList: PropertyIdData
    lateinit var  selectedProfile: ObjSelectedProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        creatorDetailsViewModel = ViewModelProvider(this)[CreatorDetailsViewModel::class.java]
        creatorDetailsPageBinding = DataBindingUtil.inflate(inflater,
            R.layout.creator_details_page, container, false)
        creatorDetailsPageBinding.creatorListDetailsViewModel = creatorDetailsViewModel
        creatorDetailsPageBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        creatorDetailsViewModel.iCreatorDetailsListener  = this

        creatorDetailsPageBinding.drawerViewModel = drawerViewModel
        getBundleData()
        apiCall()
        return creatorDetailsPageBinding.root
    }


    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.real_estate))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun getBundleData(){
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.PROPERTY_DETAILS) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_DETAILS) as RealCreatorDatum
                propertyId = property.profileID
            }
            if (args.getSerializable(BaseConstant.PROPERTY_ID) != null) {
                val property =  args.getSerializable(BaseConstant.PROPERTY_ID).toString()
                propertyId = property
            }

            if(args.getSerializable("profile") !=  null){
              selectedProfile = requireArguments().getSerializable("profile") as ObjSelectedProfile

            }

        }
    }






        fun apiCall(){
            var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
            language?.let { creatorDetailsViewModel.apiCallRepo(it,"1") }
        }
    override fun onClickBack() {
        val bundle = Bundle()
        bundle.putSerializable("profile", selectedProfile)
        findNavController().navigate(R.id.action_creatorDetailsFragment_to_creatorListFragment,bundle)
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onWhatsAppClick() {
        val phoneNumber = "+1234567890" // Replace with the phone number without '+' or '-' or '()'
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
        TODO("Not yet implemented")
    }

    override fun onEmailClick() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message body")

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No email app installed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCallClick() {
        val phoneNumber = "+1234567890" // Replace with the phone number you want to call

        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

        try {
            startActivity(callIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Calling is not supported on this device.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSuccessGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        showDialog(
            objDetailsCreatorRes.getSingalRealCreatorDetailsResponse.singalRealCreatorData.toString(),
            false,
            false
        )
    }

    override fun onFailureGetRealCreatorList(objDetailsCreatorRes: ObjDetailsCreatorRes) {
        showDialog(
            objDetailsCreatorRes.singalRealCreatorResponse.responseStatusHeader.statusDescription,
            false,
            false
        )
    }
}