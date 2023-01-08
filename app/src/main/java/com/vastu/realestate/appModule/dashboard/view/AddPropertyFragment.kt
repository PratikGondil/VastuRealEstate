package com.vastu.realestate.appModule.dashboard.view

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vastu.addproperty.model.request.AddPropertyRequest
import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.editproperty.model.request.EditPropertyRequest
import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAddPropertyListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.VastuDashboardFragment.Companion.userId
import com.vastu.realestate.appModule.dashboard.view.VastuDashboardFragment.Companion.userType
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
import com.vastu.realestate.utils.BaseConstant.PICK_FROM_GALLERY
import com.vastu.realestatecore.model.response.PropertyData
import java.io.*
import java.net.URISyntaxException
import java.util.*


class AddPropertyFragment : BaseFragment(), IToolbarListener,IAddPropertyListener,View.OnTouchListener  {
    private lateinit var addPropertyViewModel: AddPropertyViewModel
    private lateinit var addPropertyBinding:FragmentAddPropertyBinding
    private lateinit var drawerViewModel: DrawerViewModel
    private var objSubAreaReq = ObjSubAreaReq()
    private var addPropertyRequest = AddPropertyRequest()
    private var ediPropertyRequest = EditPropertyRequest()
    private lateinit var image1:String
    private lateinit var image2:String
    private lateinit var image3:String
    private lateinit var image4:String
    private lateinit var image5:String
    private lateinit var thumbnail:String
    private lateinit var brouche :String
    private var stepImage:Int = 0
    private var isEdit:Boolean = false


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
        addPropertyBinding.addPropertyViewModel = addPropertyViewModel
        addPropertyBinding.drawerViewModel = drawerViewModel
        getBundleData()
        initViewsList()
        return addPropertyBinding.root
    }

    private fun getBundleData(){
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(IS_FROM_PROPERTY_LIST) != null) {
                isEdit =  args.getSerializable(IS_FROM_PROPERTY_LIST) as Boolean
            }
        }
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

    override fun onEditProperty() {
       isEdit = true;
    }

    override fun onClickUploadImage(image:Int) {
        if(checkRequestPermissions(PICK_FROM_GALLERY)){
             stepImage = image
             chooseImageFromGallery()
            }
    }

    override fun onClickAddProperty() {
        showProgressDialog()
        if(isEdit){
            addPropertyViewModel.callEditPropertyApi(getEditPropertyData())
        }else {
            addPropertyViewModel.callAddPropertyApi(getAddPropertyData())
        }
    }

    override fun onFailureEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(editPropertyMainResponse.editPropertyResponse.responseStatusHeader.statusDescription,false,false)

    }

    override fun onSuccessEditProperty(editPropertyMainResponse: EditPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(editPropertyMainResponse.editPropertyResponse.responseStatusHeader.statusDescription,true,false)
    }

    private fun getEditPropertyData():EditPropertyRequest {
        ediPropertyRequest = ediPropertyRequest.copy(
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
        showDialog(addPropertyMainResponse.registerResponse.responseStatusHeader.statusDescription,true,false)
    }

    override fun onFailureAddProperty(addPropertyMainResponse: AddPropertyMainResponse) {
        hideProgressDialog()
        clearAllFields()
        showDialog(addPropertyMainResponse.registerResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
      hideProgressDialog()
      showDialog(objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
      hideProgressDialog()
      showDialog(objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
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
                        var filePath = getPath(requireContext(), uri)
                        filePath = filePath?.let { saveBitmapToFile(requireContext(), it, false) }
                        if (checkFileSize(filePath)) {
                            val bitmap = getBitmap(filePath)//uri?.let { uriToBitmap(it) }//MediaStore.Images.Media.getBitmap(requireContext()?.contentResolver, uri)
                            filePath?.let { setImagePath(it,bitmap) }
                        } else {
                            showDialog("File must be less than 2MB",false,false)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed to select image!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun setImagePath(filePath:String,bitmap: Bitmap?){
        addPropertyBinding.apply {
            when(stepImage){
                1->{
                    imagePath1.text = filePath
                    image1 = convertToBase64(bitmap)
                }
                2->{
                    imagePath2.text = filePath
                    image2 = convertToBase64(bitmap)
                }
                3->{
                    imagePath3.text = filePath
                    image3 = convertToBase64(bitmap)
                }
                4->{
                    imagePath4.text = filePath
                    image4 = convertToBase64(bitmap)
                }
                5->{
                    imagePath5.text = filePath
                    image5 = convertToBase64(bitmap)
                }
                6->{
                    brouchePath.text = filePath
                    //image5 = convertToBase64(bitmap)
                }
                else->{
                    thumbnailImage.setImageBitmap(bitmap)
                    thumbnail = convertToBase64(bitmap)
                }
            }
        }

    }

    private fun getBitmap(filePath: String?): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        val scaleByHeight: Boolean =
            Math.abs(options.outHeight - 100) >= Math
                .abs(options.outWidth - 100)
        if (options.outHeight * options.outWidth * 2 >= 16384) {
            val sampleSize: Double =
                if (scaleByHeight) (options.outHeight / 100).toDouble() else (options.outWidth / 100).toDouble()
            options.inSampleSize = Math.pow(
                2.0, Math.floor(
                    Math.log(sampleSize) / Math.log(2.0)
                )
            ).toInt()
        }
        options.inJustDecodeBounds = false
        options.inTempStorage = ByteArray(512)
        return BitmapFactory.decodeFile(filePath, options)
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
        val permissions = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (!hasPermissions(activity, permissions)) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
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
        if (grantResults.size > 0) {
            for (i in permissions.indices) perms[permissions[i]] = grantResults[i]
            if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
            ) {
                if(checkRequestPermissions(PICK_FROM_GALLERY)){
                    chooseImageFromGallery()
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

    private fun saveBitmapToFile(context: Context, imageUri: String, isPNG: Boolean): String? {
        val filePath = getRealPathFromURI(context, imageUri)
        var scaledBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        val maxHeight = 816.0f
        val maxWidth = 612.0f
        var imgRatio = actualWidth.toFloat() / actualHeight
        val maxRatio = maxWidth / maxHeight
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
        options.inSampleSize = calculateInSampleSize(
            options,
            actualWidth,
            actualHeight
        )
        options.inJustDecodeBounds = false
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        try {
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        var canvas: Canvas? = null
        try {
            if (scaledBitmap != null) {
                canvas = Canvas(scaledBitmap)
            }
            canvas!!.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                (middleX - bmp.width.toDouble() / 2).toFloat(),
                middleY - (bmp.height.toDouble() / 2).toFloat(),
                Paint(
                    Paint.FILTER_BITMAP_FLAG
                )
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        val exif: ExifInterface
        val matrix = Matrix()
        try {
            if (!isPNG) {
                exif = ExifInterface(filePath!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                if (orientation == 6) {
                    matrix.postRotate(90f)
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                }
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap!!,
                0,
                0,
                scaledBitmap.width,
                scaledBitmap.height,
                matrix,
                true
            )
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var out: FileOutputStream? = null
        val filename: String? = getFilename()
        try {
            out = FileOutputStream(filename)
            if (isPNG) {
                scaledBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, out)
            } else {
                scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return filename
    }

    private fun getRealPathFromURI(context: Context, contentURI: String): String? {
        val contentUri = Uri.parse(contentURI)
        val cursor = context.contentResolver.query(contentUri, null, null, null, null)
        return if (cursor == null) {
            contentUri.path
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
            cursor.getString(index)
        }
    }
    private fun getFilename(): String? {
        var file: File? = null
        file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "Vastu/Images"
        ) else File(Environment.getExternalStorageDirectory().path, "Vastu/Images")
        if (!file.exists()) {
            try {
                file.mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    }

    private fun checkFileSize(fileName: String?): Boolean {
        val recFIle = File(fileName)
        if (recFIle.exists()) {
            val length = recFIle.length()
            return length > 0 && length < 2000001
        }
        return false
    }
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = width.toFloat() * height
        val totalReqPixelsCap = reqWidth.toFloat() * reqHeight * 2
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
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
                if (getString(R.string.image_string).equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if (getString(R.string.video_string).equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if (getString(R.string.audio_string).equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = getString(R.string.id_string)
                selectionArgs = arrayOf(split[1])
            }
        }
        if (getString(R.string.content_string).equals(uri.scheme)) {
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
        } else if (getString(R.string.file_string).equals(uri.scheme)) {
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
}