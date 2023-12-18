package com.vastu.realestate.appModule.realCreator.infoPage

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.signUp.bindingAdapter.SignUpBindingAdapter
import com.vastu.realestate.databinding.FindProfileFragmentBinding
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.PreferenceManger
import java.util.*

class FindProfileFragment: BaseFragment(), View.OnTouchListener,IToolbarListener, FindProfileViewListener {

    lateinit var viewModel: FindProfileViewModel
    lateinit var drawerViewModel: DrawerViewModel
    var isdialogDisplayed:Boolean=false
    lateinit var findProfileFragmentBinding: FindProfileFragmentBinding
    var objSubAreaReq = ObjSubAreaReq()
    private var lat: Double?=null
    private var long :Double?=null
    private var address : String?=null
    private var PERMISSION_ID = 52
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var selectetdTalukaID :String? =null
    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation =p0.lastLocation
            lat = lastLocation?.latitude
            long = lastLocation?.longitude
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FindProfileViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        findProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.find_profile_fragment, container, false)
       findProfileFragmentBinding.findProfileViewModel=viewModel
        drawerViewModel.iToolbarListener = this
        viewModel.findProfileViewListener = this
        findProfileFragmentBinding.drawerViewModel = drawerViewModel

        initView()
        getCityList()
        observeCityList()
        observeCity()
        observeSubAreaList()

        return findProfileFragmentBinding.root
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.feedback))
        drawerViewModel.isDashBoard.set(false)
        setSpinner()
        findProfileFragmentBinding.autoCompleteCity.setOnTouchListener(this)
        findProfileFragmentBinding.autoCompleteAreaList.setOnTouchListener(this)
    }
    @SuppressLint("ResourceType")
    private fun setSpinner() {
        lateinit var adapter:List<String>
        var isMarathi = false
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        if(language.equals(Constants.MARATHI)){
            adapter  =viewModel.allQueryMarathi.get()!!
            isMarathi = true
        }else{
            isMarathi = false
            adapter  =viewModel.allQuery.get()!!
        }
        findProfileFragmentBinding.spinner.adapter = ArrayAdapter(
            requireContext(), R.layout.drop_down_item, adapter
        ).also {
                adapter->
            adapter.setDropDownViewResource(R.drawable.drop_down_icon)
        }

        findProfileFragmentBinding.spinner2.adapter = ArrayAdapter(
            requireContext(),                R.layout.drop_down_item, adapter
        )



        findProfileFragmentBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedItem = ""
                if(isMarathi){
                    selectedItem = viewModel.allQueryMarathi.get()!!.get(position)
                    viewModel.selectedQuery.set(selectedItem)
                }else{
                    selectedItem = viewModel.allQuery.get()!!.get(position)
                    viewModel.selectedQuery.set(selectedItem)
                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



    }



    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
        showDialog(objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
        showDialog(objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onUserNotConnected() {
        TODO("Not yet implemented")
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
            locationRequest,locationCallback, Looper.myLooper()
        )
    }

    override fun onResume() {
        super.onResume()

    }


    private fun getCityList(){
        var language =PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        viewModel.callCityListApi(language)

    }
    private fun callSubAreaList(talukaId: String) {
        var language =PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        objSubAreaReq = objSubAreaReq.copy(talukaId,language!!)
        viewModel.callSubAreaList(objSubAreaReq)
    }


    private fun observeCityList(){
        viewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            val adapter: ArrayList<ObjTalukaDataList> =cityList
            findProfileFragmentBinding.autoCompleteCity.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )

            findProfileFragmentBinding.autoCompleteCity.setText(adapter.get(0).taluka);
            selectetdTalukaID = adapter.get(0).talukaId
            viewModel.city.value = adapter.get(0)
            findProfileFragmentBinding.tilcity.helperText=""
            SignUpBindingAdapter.isValidCity = true
        }


    }
    private fun observeCity(){
        viewModel.city.observe(viewLifecycleOwner){city->
            if (city != null) {
                callSubAreaList(selectetdTalukaID!!)
            }
        }
    }
    private fun observeSubAreaList(){
        viewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            val adapter: ArrayList<ObjCityAreaData> =subAreaList
            findProfileFragmentBinding.autoCompleteAreaList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }

    override fun onClickBack() {
        TODO("Not yet implemented")
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onSubmitBtnClick() {
        findNavController().navigate(R.id.action_findProfileFragment_to_creatorListFragment)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}