package com.vastu.realestate.appModule.signUp.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ITermsConditionListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.signUp.bindingAdapter.SignUpBindingAdapter
import com.vastu.realestate.appModule.signUp.uiInterfaces.ISignUpViewListener
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.databinding.SignUpFragmentBinding
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterDlts
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.BaseConstant.CUSTOMER
import com.vastu.realestate.utils.BaseConstant.REGISTER_DTLS_OBJ
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.termsandconditions.model.respone.TermsConditionData
import com.vastu.termsandconditions.model.respone.TermsConditionMainResponse
import java.util.*


class SignUpFragment : BaseFragment(),View.OnTouchListener, ISignUpViewListener,ITermsConditionListener {

    private lateinit var signUpViewModel: SignUpViewModel
    lateinit var signUpFragmentBinding: SignUpFragmentBinding
    lateinit var viewPager :ViewPager2
    var objUserInfo= ObjUserInfo()
    var objSubAreaReq = ObjSubAreaReq()
    private var lat: Double?=null
    private var long :Double?=null
    private var address : String?=null

    private var PERMISSION_ID = 52
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest:LocationRequest
    private var selectetdTalukaID :String? =null
    private var isdialogDisplayed :Boolean = false
//private var isScrollView:Boolean = false

    private val locationCallback = object:LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation =p0.lastLocation
            lat = lastLocation?.latitude
            long = lastLocation?.longitude
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        signUpFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.sign_up_fragment,container,false)
        signUpFragmentBinding.lifecycleOwner = this
        signUpFragmentBinding.signUpViewModel = signUpViewModel
        signUpViewModel.iSignUpViewListener =this
        viewPager = activity?.findViewById(R.id.vpSignUpLogin)!!
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams
            .SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastLocation()
        initView()
        getCityList()
        observeCityList()
        observeCity()
        observeSubAreaList()
        return signUpFragmentBinding.root
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location :Location? = task.result
                    if (location==null){
                        getNewLocation()
                    }else{
                        lat = location.latitude
                        long = location.longitude
                        signUpFragmentBinding.edtAddress.setText(getAddress(lat!!, long!!))
                        address = signUpFragmentBinding.edtAddress.text.toString()

                    }
                }
            }else{
                buildAlertMessageNoGps()
            }
        }else{
            requestPermission()
        }
    }


    private fun buildAlertMessageNoGps() {
        if(!isdialogDisplayed) {
            isdialogDisplayed = true
            var gpsbuilder = AlertDialog.Builder(requireContext())
            gpsbuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            gpsbuilder.setTitle("GPS Settings")
                .setCancelable(true)
                .setPositiveButton("Settings",
                    DialogInterface.OnClickListener { dialog, id ->startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                )
            var alert = gpsbuilder.create()
            alert.show()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getNewLocation(){
        locationRequest= LocationRequest()
        locationRequest.priority =LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval =0
        locationRequest.numUpdates =2
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,locationCallback,Looper.myLooper()
        )
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
        if(address==null)
        {
            Handler().postDelayed({ getLastLocation() }, 3000)
        }
    }

    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED ||
           ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
           return true
        }
        return false
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(),
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_ID)
    }

    private fun isLocationEnabled():Boolean{
        val locationManager:LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun getAddress(latitude:Double,longitude:Double):String{
        val geoCoder = Geocoder(activity,Locale.getDefault())
        val add = geoCoder.getFromLocation(latitude,longitude,1).get(0).getAddressLine(0)
        return add
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView(){
        signUpFragmentBinding.autoCompleteCity.setOnTouchListener(this)
        signUpFragmentBinding.autoCompleteAreaList.setOnTouchListener(this)


    }
    private fun observeCityList(){
        signUpViewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            val adapter: ArrayList<ObjTalukaDataList> =cityList
            signUpFragmentBinding.autoCompleteCity.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )

            signUpFragmentBinding.autoCompleteCity.setText(adapter.get(0).taluka);
            selectetdTalukaID = adapter.get(0).talukaId
            signUpViewModel.city.value = adapter.get(0)
            signUpFragmentBinding.tilcity.helperText=""
            SignUpBindingAdapter.isValidCity = true
        }


    }
    private fun observeCity(){
        signUpViewModel.city.observe(viewLifecycleOwner){city->
            if (city != null) {
                  callSubAreaList(selectetdTalukaID!!)
                }
        }
    }
    private fun observeSubAreaList(){
        signUpViewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            val adapter: ArrayList<ObjCityAreaData> =subAreaList
            signUpFragmentBinding.autoCompleteAreaList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }



    override fun launchOtpScreen(objRegisterDlts: ObjRegisterDlts) {
        hideProgressDialog()
        val bundle = Bundle()
        bundle.putSerializable(REGISTER_DTLS_OBJ, objRegisterDlts)
        clearAllFields()
        showDialog(getString(R.string.register_successfully),isSuccess = true,isNetworkFailure = false)
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
        Handler(Looper.getMainLooper()).postDelayed({
            hideDialog()}, 1000)
    }

    private fun getUserInfo(){
        var language =PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)

        objUserInfo =objUserInfo.copy(firstName = signUpViewModel.firstName.get()!!,
        middleName = signUpViewModel.middleName.get()!!,
        lastName = signUpViewModel.lastName.get()!!,
        mobile = signUpViewModel.mobileNumber.get()!!,
        city = signUpViewModel.city.value!!.talukaId,
        subArea = signUpViewModel.subArea.get()!!.areaId!!,
        emailId = signUpViewModel.mailId.get()!!,
        userType = CUSTOMER,
        latitude = lat.toString(),
        longitude = long.toString(),
        address = address,
        language = language
        )
    }

    override fun registerUser(){
        redirectAftertheTermsAccept()
    }

    private fun getCityList(){
        var language =PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        signUpViewModel.callCityListApi(language)

    }
    private fun callSubAreaList(talukaId: String) {
        var language =PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        objSubAreaReq = objSubAreaReq.copy(talukaId,language!!)
        signUpViewModel.callSubAreaList(objSubAreaReq)
    }
    override fun goToLogin() {
        hideProgressDialog()
        clearAllFields()
        showDialog(getString(R.string.register_successfully),false,false)
        Handler(Looper.getMainLooper()).postDelayed({
            viewPager.currentItem = 0
            hideDialog()

        }, 1000)

    }
    private fun showTermsConditionDialog(){
        signUpViewModel.callTermsAndCondition()
    }

    override fun onRegistrationFail(objRegisterResponseMain: ObjRegisterResponseMain) {
        hideProgressDialog()
        clearAllFields()
        showDialog(objRegisterResponseMain.objRegisterResponse.objResponseStatusHdr.statusDescr,false,false)
        Handler(Looper.getMainLooper()).postDelayed({
            hideDialog()}, 1000)

    }

    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
       showDialog(objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
        showDialog(objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onSuccessTermsCondition(termsConditionMainResponse: TermsConditionMainResponse) {
       if(termsConditionMainResponse.gettermsConditionDetailsResponse.termsConditionData!=null){
           showTermsConditionBottomSheet(termsConditionMainResponse.gettermsConditionDetailsResponse.termsConditionData)
       }
    }
    private fun showTermsConditionBottomSheet(termsConditionData: List<TermsConditionData>){
        try{
            val bottomSheetFragment = TermsConditionsScreen(this,termsConditionData)
          bottomSheetFragment.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar)
            bottomSheetFragment.show(requireActivity().supportFragmentManager,bottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onFailureTermsCondition(termsConditionMainResponse: TermsConditionMainResponse) {
        showDialog(termsConditionMainResponse.termsConditionResponse.responseStatusHeader.statusDescription!!,isSuccess = false,isNetworkFailure = false)
    }

    override fun onSuccessTerms() {

    }

    override fun onFailureTerms() {

    }

    fun redirectAftertheTermsAccept()
    {
        showProgressDialog()
        getUserInfo()
        signUpViewModel.callRegistrationApi(objUserInfo)

    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }

    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == signUpFragmentBinding.autoCompleteCity) {
            view.let {
                onShowStateDropDown(it)
            }
        }
        else if(view == signUpFragmentBinding.autoCompleteAreaList){
            view.let {
                onShowStateDropDown(it)
            }
        }
        return true
    }
    private fun clearAllFields(){
       signUpFragmentBinding.apply {
            edtFirstName.text?.clear()
            edtMiddleName.text?.clear()
            edtLastName.text?.clear()
            edtMobileNum.text?.clear()
            edtEmail.text?.clear()
            edtAddress.text?.clear()
        }
    }

    override fun onAcceptTerms() {
        viewPager.currentItem = 0
    }

    override fun onRejectTerms() {
        showDialog(getString(R.string.terms_condition),isSuccess = false,isNetworkFailure = false)
    }
}
