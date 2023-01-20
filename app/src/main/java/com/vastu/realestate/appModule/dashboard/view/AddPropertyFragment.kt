package com.vastu.realestate.appModule.dashboard.view

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.util.Base64
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vastu.addproperty.model.request.AddPropertyRequest
import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.deleteimage.model.request.DeleteImageRequest
import com.vastu.deleteimage.model.response.DeleteImageMainResponse
import com.vastu.editproperty.model.request.EditPropertyRequest
import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.getimages.model.request.GetImageRequest
import com.vastu.getimages.model.response.GetImageMainResponse
import com.vastu.getimages.model.response.ImageData
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.PropertyImagesAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAddPropertyListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IGetImagesListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertyDetailsListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userType
import com.vastu.realestate.appModule.dashboard.viewmodel.AddPropertyViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.FragmentAddPropertyBinding
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.BaseConstant.ADD_PROPERTY_STATUS
import com.vastu.realestate.utils.BaseConstant.IS_FROM_PROPERTY_LIST
import com.vastu.realestate.utils.BaseConstant.PDF_SELECTION
import com.vastu.realestate.utils.BaseConstant.PICK_FROM_GALLERY
import com.vastu.realestate.utils.CommonUtils
import com.vastu.realestatecore.model.response.PropertyData
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException
import java.net.URISyntaxException
import java.util.*
import kotlin.collections.ArrayList


class AddPropertyFragment : BaseFragment(), IToolbarListener,IAddPropertyListener,View.OnTouchListener,IPropertyDetailsListener,IGetImagesListener,
    PropertyImagesAdapter.OnItemClickListener {
    private lateinit var addPropertyViewModel: AddPropertyViewModel
    private lateinit var addPropertyBinding:FragmentAddPropertyBinding
    private lateinit var drawerViewModel: DrawerViewModel
    private var objSubAreaReq = ObjSubAreaReq()
    private var addPropertyRequest = AddPropertyRequest()
    private var ediPropertyRequest = EditPropertyRequest()
    private var getImageRequest = GetImageRequest()
    private var deleteImageRequest = DeleteImageRequest()
    private lateinit var imageList :List<ImageData>
    private var image1=String()
    private var image2=String()
    private var image3=String()
    private var image4=String()
    private var image5=String()
    private var thumbnail=String()
    private var brouche =String()
    private var stepImage:Int = 0
    private var isEdit:Boolean = false
    private var isValid:Boolean = false
    private var propertyId : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        addPropertyViewModel = ViewModelProvider(this)[AddPropertyViewModel::class.java]
        addPropertyBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_property, container, false)
        addPropertyBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        addPropertyViewModel.iAddPropertyListener = this
        addPropertyViewModel.iPropertyDetailsListener = this
        addPropertyViewModel.iGetImagesListener = this
        addPropertyBinding.addPropertyViewModel = addPropertyViewModel
        addPropertyBinding.drawerViewModel = drawerViewModel
        getBundleData()
        initViewsList()
        return addPropertyBinding.root
    }

    private fun getBundleData() {
        val args = this.arguments
        if (args != null) {
            if (args.getSerializable(IS_FROM_PROPERTY_LIST) != null) {
                isEdit = args.getSerializable(IS_FROM_PROPERTY_LIST) as Boolean
                if (isEdit) {
                    addPropertyBinding.tvPropertyTitle.text =
                        getString(R.string.edit_up_the_property_details)
                    editPropertyImages()
                    if (args.getSerializable(BaseConstant.PROPERTY_DETAILS) != null) {
                        val property =
                            args.getSerializable(BaseConstant.PROPERTY_DETAILS) as PropertyData
                        propertyId = property.propertyId
                        addPropertyBinding.btnPostProperty.setText(getString(R.string.edit_property))
                        getPropertyDetails()
                    }
                }
            }
        }
    }

    private fun editPropertyImages(){
        addPropertyBinding.uploadImageLayout.setVisibility(View.GONE)
    }
    private fun initViewsList(){
            addPropertyBinding.autoCompletePropertyType.setOnTouchListener(this)
            addPropertyBinding.autoCompleteSellType.setOnTouchListener(this)
            addPropertyBinding.autoCompleteBuildYear.setOnTouchListener(this)
            addPropertyBinding.autoCompleteAvailability.setOnTouchListener(this)
            addPropertyBinding.autoCompleteCity.setOnTouchListener(this)
            addPropertyBinding.autoCompleteSubAreaList.setOnTouchListener(this)

        val sellTypeList = arrayOf("Sell","Rent")
        val sellTypeAdapter: Array<String> = sellTypeList
        addPropertyBinding.autoCompleteSellType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, sellTypeAdapter
            )
        )

        val propertyTypeList = arrayOf("1 BHK", "2 BHK","3 BKH","4 BHK","5 BHK","6 BHK","7 BHK","8 BHK","Apartment","Home","Offcie")
        val propertyTypeAdapter: Array<String> = propertyTypeList
        addPropertyBinding.autoCompletePropertyType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, propertyTypeAdapter
            )
        )

        val availabilityList = arrayOf("Available","Not Available","Coming Soon")
        val availabilityAdapter: Array<String> = availabilityList
        addPropertyBinding.autoCompleteAvailability.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, availabilityAdapter
            )
        )

        val buildYearList = ArrayList<String>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in thisYear downTo 1950) {
            buildYearList.add(i.toString())
        }

        val buildYearAdapter: ArrayList<String> = buildYearList
        addPropertyBinding.autoCompleteBuildYear.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, buildYearAdapter
            )
        )
        callCityList()
    }
    override fun onResume() {
        super.onResume()
        if(isEdit)
            drawerViewModel.toolbarTitle.set(getString(R.string.edit_property))
        else
            drawerViewModel.toolbarTitle.set(getString(R.string.add_property))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun callCityList(){
       addPropertyViewModel.callCityListApi()
       observeCityList()
       observeCity()
    }
    private fun observeCityList(){
       addPropertyViewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            val adapter: ArrayList<ObjTalukaDataList> =cityList

            addPropertyBinding.autoCompleteCity.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }
    private fun observeCity(){
       addPropertyViewModel.city.observe(viewLifecycleOwner){city->
            if (city != null) {
                callSubAreaList(city.talukaId!!)
            }
        }
    }
    private fun callSubAreaList(id: String) {
        objSubAreaReq = ObjSubAreaReq().copy(talukaId = id )
        addPropertyViewModel.callSubAreaList(objSubAreaReq)
        observeSubAreaList()
    }
    private fun observeSubAreaList(){
        addPropertyViewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            val adapter: ArrayList<ObjCityAreaData> =subAreaList
           addPropertyBinding.autoCompleteSubAreaList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }

    override fun onClickBrochure() {
        if(checkRequestPermissions(PICK_FROM_GALLERY)) {
           pdfSelection()
        }
    }
    private fun pdfSelection(){
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        activity?.startActivityForResult(pdfIntent, 12)
    }


    override fun onClickUploadImage(image:Int) {
        if(checkRequestPermissions(PICK_FROM_GALLERY)){
             stepImage = image
             chooseImageFromGallery()
            }
    }

    override fun onClickAddProperty() {
        showProgressDialog()
        if(isEdit && isValid){
            addPropertyViewModel.callEditPropertyApi(getEditPropertyData())
        }else {
            addPropertyViewModel.callAddPropertyApi(getAddPropertyData())
        }
    }

    override fun onFailureEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(editPropertyMainResponse.editPropertyResponse.responseStatusHeader.statusDescription, isSuccess = false, isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({  onClickBack() }, 1000)
    }

    override fun onSuccessEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(editPropertyMainResponse.editPropertyResponse.responseStatusHeader.statusDescription, isSuccess = true, isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({  onClickBack() }, 1000)
    }

    private fun getEditPropertyData():EditPropertyRequest {
        ediPropertyRequest = ediPropertyRequest.copy(
            propertyId = propertyId,
            userId = userId,
            propertyTitle = addPropertyViewModel.propertyTitle.get(),
            propertyType = addPropertyViewModel.propertyType.get(),
            sellType = addPropertyViewModel.sellType.get(),
            buildYear = addPropertyViewModel.buildYear.get(),
            state = addPropertyViewModel.state.get(),
            cityId = addPropertyViewModel.city.value!!.talukaId,
            areaId = addPropertyViewModel.subArea.value!!.areaId,
            address = addPropertyViewModel.propertyAddress.get(),
            area = addPropertyViewModel.areaSqFt.get(),
            price = addPropertyViewModel.price.get(),
            bookingAmount = addPropertyViewModel.bookingAmount.get(),
            bathroom = addPropertyViewModel.bathroom.get(),
            bedroom = addPropertyViewModel.bedroom.get(),
            kitchen = addPropertyViewModel.kitchen.get(),
            balcony = addPropertyViewModel.balcony.get(),
            swimmingPool = addPropertyViewModel.swimmingPool.get(),
            garage = addPropertyViewModel.garage.get(),
            floors = addPropertyViewModel.floors.get(),
            brochure = addPropertyViewModel.brochurePdf.get(),
            description = addPropertyViewModel.description.get(),
            highlights = addPropertyViewModel.highlights.get(),
            availability = addPropertyViewModel.availability.get(),
            amenities = addPropertyViewModel.amenties.get(),
            img1 = image1,
            img2 = image2,
            img3 = image3,
            img4 = image4,
            img5 = image5)
        return ediPropertyRequest
    }
    private fun getAddPropertyData():AddPropertyRequest {
       addPropertyRequest = addPropertyRequest.copy(
           userId = userId,
           userType = userType,
           propertyTitle = addPropertyViewModel.propertyTitle.get(),
           propertyType = addPropertyViewModel.propertyType.get(),
           sellType = addPropertyViewModel.sellType.get(),
           buildYear = addPropertyViewModel.buildYear.get(),
           state = addPropertyViewModel.state.get(),
           cityId = addPropertyViewModel.city.value!!.talukaId,
           areaId = addPropertyViewModel.subArea.value!!.areaId,
           address = addPropertyViewModel.propertyAddress.get(),
           area = addPropertyViewModel.areaSqFt.get(),
           price = addPropertyViewModel.price.get(),
           bookingAmount = addPropertyViewModel.bookingAmount.get(),
           bathroom = addPropertyViewModel.bathroom.get(),
           bedroom = addPropertyViewModel.bedroom.get(),
           kitchen = addPropertyViewModel.kitchen.get(),
           balcony = addPropertyViewModel.balcony.get(),
           swimmingPool = addPropertyViewModel.swimmingPool.get(),
           garage = addPropertyViewModel.garage.get(),
           floors = addPropertyViewModel.floors.get(),
           brochure = addPropertyViewModel.brochurePdf.get(),
           thumbnail = thumbnail,
           description = addPropertyViewModel.description.get(),
           highlights = addPropertyViewModel.highlights.get(),
           availability = addPropertyViewModel.availability.get(),
           amenities = addPropertyViewModel.amenties.get(),
           slug = "",
           status = ADD_PROPERTY_STATUS,
           img1 = image1,
           img2 = image2,
           img3 = image3,
           img4 = image4,
           img5 = image5)
        return addPropertyRequest
    }

    override fun onSuccessAddProperty(addPropertyMainResponse: AddPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(addPropertyMainResponse.registerResponse.responseStatusHeader.statusDescription, isSuccess = true, isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({
            onClickBack()
            hideDialog()}, 1000)
    }

    override fun onFailureAddProperty(addPropertyMainResponse: AddPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(addPropertyMainResponse.registerResponse.responseStatusHeader.statusDescription, isSuccess = false, isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({
            onClickBack()
            hideDialog()}, 1000)
    }

    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
      hideProgressDialog()
      showDialog(objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr, isSuccess = false, isNetworkFailure = false)
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
      hideProgressDialog()
      showDialog(objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr, isSuccess = false, isNetworkFailure = false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("", isSuccess = false, isNetworkFailure = true)
    }
    private fun clearAllFields(){
       addPropertyBinding.apply {
            edtPropertyTitle.text?.clear()
            edtPrice.text?.clear()
            edtBookingAmount.text?.clear()
            edtPropertyAddress.text?.clear()
            edtStates.text?.clear()
            edtArea.text?.clear()
            edtDescription.text?.clear()
            edtHighlights.text?.clear()
            edtBedroom.text?.clear()
            edtBalcony.text?.clear()
            edtKitchen.text?.clear()
            edtSwimmingPool.text?.clear()
            edtBalcony.text?.clear()
            edtGarage.text?.clear()
            edtFloors.text?.clear()
            thumbnailImage.setImageResource(R.drawable.hom)
        }
    }
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        when (view) {
            addPropertyBinding.autoCompletePropertyType -> {
                onShowStateDropDown(view)
            }
            addPropertyBinding.autoCompleteSellType -> {
                onShowStateDropDown(view)
            }
            addPropertyBinding.autoCompleteBuildYear -> {
                onShowStateDropDown(view)
            }
            addPropertyBinding.autoCompleteAvailability->{
                onShowStateDropDown(view)
            }
            addPropertyBinding.autoCompleteCity->{
                onShowStateDropDown(view)
            }
            addPropertyBinding.autoCompleteSubAreaList->{
                onShowStateDropDown(view)
            }
        }
        return true
    }
    private fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        //empty
    }

    override fun onClickNotification() {
        //empty
    }
    private fun chooseImageFromGallery(){
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(intent,PICK_FROM_GALLERY
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        if (requestCode == PICK_FROM_GALLERY) {
            if (data != null) {
                val uri: Uri? = data.data
                try {
                    if (uri != null) {
                        val filePath = getPath(requireContext(), uri)
                        val bitmap = uriToBitmap(uri)
                        filePath?.let { setImagePath(it,bitmap) }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed to select image!", Toast.LENGTH_SHORT).show()
                }
            }
        }else if(resultCode == PDF_SELECTION){

        }
    }
    private fun setImagePath(filePath:String,bitmap: Bitmap?){
        addPropertyBinding.apply {
            when(stepImage){
                1->{
                    imagePath1.text = getImageName(filePath)
                    image1 = convertToBase64(bitmap)
                }
                2->{
                    imagePath2.text = getImageName(filePath)
                    image2 = convertToBase64(bitmap)
                }
                3->{
                    imagePath3.text = getImageName(filePath)
                    image3 = convertToBase64(bitmap)
                }
                4->{
                    imagePath4.text = getImageName(filePath)
                    image4 = convertToBase64(bitmap)
                }
                5->{
                    imagePath5.text = getImageName(filePath)
                    image5 = convertToBase64(bitmap)
                }else->{
                    thumbnailImage.setImageBitmap(bitmap)
                    thumbnail = convertToBase64(bitmap)
                }
            }
        }
    }
    private fun getImageName(path:String):String{
     return path.substring(path.lastIndexOf("/") + 1)
    }
    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    private fun convertToBase64(bitmap:Bitmap?):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun checkRequestPermissions(permissionID: Int): Boolean {
        val permissionCamera =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        val readExternalPermission =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeExternalPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toTypedArray(), permissionID)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val perms: MutableMap<String, Int> = HashMap()
        perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
        if (grantResults.size > 0) {
            for (i in permissions.indices) perms[permissions[i]] = grantResults[i]
            if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
            ) {
                if(checkRequestPermissions(PICK_FROM_GALLERY)){
                    chooseImageFromGallery()
                }
                if(checkRequestPermissions(PDF_SELECTION)){
                    pdfSelection()
                }
            }
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    @Throws(URISyntaxException::class)
    private fun getPath(context: Context, uri: Uri): String? {
        var uri = uri
        val needToCheckUri = Build.VERSION.SDK_INT >= 19
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.applicationContext, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                return Environment.getExternalStorageDirectory()
                    .toString() + " " + getString(R.string.slash) + " " + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                when (type) {
                    getString(R.string.image_string) -> {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    getString(R.string.video_string) -> {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    getString(R.string.audio_string) -> {
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                selection = getString(R.string.id_string)
                selectionArgs = arrayOf(split[1])
            }
        }
        if (getString(R.string.content_string) == uri.scheme) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor =
                    context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (getString(R.string.file_string) == uri.scheme) {
            return uri.path
        }
        return null
    }
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

   private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


    private fun getPropertyDetails(){
        hideProgressDialog()
        userId?.let {
            propertyId?.let { it1 ->
                addPropertyViewModel.getPropertyDetails(it,it1)
            }
        }
    }

    override fun onSuccessGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        val htmlPattern = "<[^>]*>"
        getImages()

        addPropertyBinding.apply {
            val property = propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0)
            edtPropertyTitle.setText(property.propertyTitle)
            edtPropertyAddress.setText(property.address)
            edtStates.setText(property.state)
            //edtHighlights.setText(property.highlights)
            //edtDescription.setText(property.description)
            edtBedroom.setText(property.bedroom)
            edtBathroom.setText(property.bathroom)
            edtKitchen.setText(property.kitchen)
            edtSwimmingPool.setText(property.swimmingPool)
            edtArea.setText(property.propertyArea)
            edtPrice.setText(property.price)
            edtBookingAmount.setText(property.bookingAmount)
            edtBalcony.setText(property.balcony)
            edtGarage.setText(property.garage)
            edtFloors.setText(property.floors)
            autoCompleteSellType.setText(property.sellType)
            autoCompletePropertyType.setText(property.propertyType)
            autoCompleteCity.setText(property.city)
            autoCompleteSubAreaList.setText(property.area)
            autoCompleteAvailability.setText(property.availability)
            autoCompleteBuildYear.setText(property.buildYear)
            CommonUtils.showImageFromURL(
                context,
                property.propertyThumbnail,
                thumbnailImage,
                R.drawable.vastu_logo_splash
            )

            if(property.highlights.length>20){
                val spannable = SpannableString(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights)
                spannable.setSpan(
                    BulletSpan(50,resources.getColor(R.color.black)), 9, 18,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(
                    BulletSpan(50, resources.getColor(R.color.black)), 20,  spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                edtHighlights.setText(Html.fromHtml(propertyDataResponseMain.getPropertyIdDetailsResponse.propertyIdData.get(0).highlights.trim()))
            }else{
                edtHighlights.setText(property.highlights)
            }
           edtDescription.setText(property.description.replace(htmlPattern," ",ignoreCase = false))
            isValid = true

        }
     }
    private fun getImages(){
        getImageRequest = getImageRequest.copy(propertyId=propertyId)
        showProgressDialog()
        addPropertyViewModel.getPropertyImages(getImageRequest)
    }

    override fun onSuccessGetImages(getImageMainResponse: GetImageMainResponse) {
        hideProgressDialog()
        if(getImageMainResponse.getImageDetailsResponse.imageData.isNotEmpty()){
            imageList = getImageMainResponse.getImageDetailsResponse.imageData
            setImages(imageList)
        }
    }
    private fun setImages(images: List<ImageData>){
        try {
            val imagesRecyclerView = addPropertyBinding.imagesGridView
            val imageAdapter = PropertyImagesAdapter(this,images)
            imagesRecyclerView.setLayoutManager(GridLayoutManager(activity, 2))
            imagesRecyclerView.adapter = imageAdapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureGetImages(getImageMainResponse: GetImageMainResponse) {
        hideProgressDialog()
        showDialog(getImageMainResponse.imageResponse.responseStatusHeader.statusDescription, isSuccess = false,isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({
            hideDialog()}, 1000)
    }

    override fun onFailureGetPropertyDetails(propertyDataResponseMain: PropertyDataResponseMain) {
        hideProgressDialog()
        showDialog(propertyDataResponseMain.propertyIdResponse.responseStatusHeader.statusDescription, isSuccess = false,isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({
            hideDialog()}, 1000)
    }

    override fun onItemClick(images: ImageData) {
           showProgressDialog()
           deleteImageRequest = deleteImageRequest.copy(propertyId=images.propertyId, imageId = images.imageId)
           addPropertyViewModel.deleteImage(deleteImageRequest)

    }
    override fun onSuccessDeleteImage(deleteImageMainResponse: DeleteImageMainResponse) {
        hideProgressDialog()
        showDialog(deleteImageMainResponse.imageDeleteResponse.responseStatusHeader.statusDescription, isSuccess = true,isNetworkFailure = false)
        getImages()
    }

    override fun onFailureDeleteImage(deleteImageMainResponse: DeleteImageMainResponse) {
        hideProgressDialog()
        showDialog(deleteImageMainResponse.imageDeleteResponse.responseStatusHeader.statusDescription, isSuccess = false,isNetworkFailure = false)
        Handler(Looper.getMainLooper()).postDelayed({
            hideDialog()}, 1000)
    }

    override fun addPropertyEnquiry() {

    }

    override fun chatEnquiry() {
    }
}