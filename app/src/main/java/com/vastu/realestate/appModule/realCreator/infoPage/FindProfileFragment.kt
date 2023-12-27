package com.vastu.realestate.appModule.realCreator.infoPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.common.collect.Sets.filter
import com.vastu.networkService.util.Constants
import com.vastu.realCreator.realCreatorSearch.model.ObjCreatorListRes
import com.vastu.realCreator.realCreatorSearch.model.ProfileDaum
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
import java.util.Locale.filter

class FindProfileFragment : BaseFragment(), View.OnTouchListener, IToolbarListener,
    FindProfileViewListener {

    lateinit var viewModel: FindProfileViewModel
    lateinit var drawerViewModel: DrawerViewModel
    var isdialogDisplayed: Boolean = false
    lateinit var findProfileFragmentBinding: FindProfileFragmentBinding
    var objSubAreaReq = ObjSubAreaReq()
    private var lat: Double? = null
    private var long: Double? = null
    private var address: String? = null
    private var PERMISSION_ID = 52
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var selectetdTalukaID: String? = null
    private var selectetdProfileID: String? = null
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
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
        findProfileFragmentBinding.findProfileViewModel = viewModel
        drawerViewModel.iToolbarListener = this
        viewModel.findProfileViewListener = this
        findProfileFragmentBinding.drawerViewModel = drawerViewModel

        initView()
        getCityList()
        getProfileList()
        observeCityList()
        observeCity()
        observeSubAreaList()
        observeProfileList()

        return findProfileFragmentBinding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.real_creator))
        drawerViewModel.isDashBoard.set(false)
        setSpinner()
        findProfileFragmentBinding.autoCompleteCity.setOnTouchListener(this)
        findProfileFragmentBinding.autoCompleteAreaList.setOnTouchListener(this)
    }

    @SuppressLint("ResourceType")
    private fun setSpinner() {
        lateinit var adapter: List<String>
        var isMarathi = false
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        if (language.equals(Constants.MARATHI)) {
            adapter = viewModel.allQueryMarathi.get()!!
            isMarathi = true
        } else {
            isMarathi = false
            adapter = viewModel.allQuery.get()!!
        }
        findProfileFragmentBinding.spinner.adapter = ArrayAdapter(
            requireContext(), R.layout.drop_down_item, adapter
        )

        findProfileFragmentBinding.spinner2.adapter = ArrayAdapter(
            requireContext(), R.layout.drop_down_item, adapter
        )



        findProfileFragmentBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var selectedItem = ""
                    if (isMarathi) {
                        selectedItem = viewModel.allQueryMarathi.get()!!.get(position)
                        viewModel.selectedQuery.set(selectedItem)
                    } else {
                        selectedItem = viewModel.allQuery.get()!!.get(position)
                        viewModel.selectedQuery.set(selectedItem)
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }


    }


    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
        showDialog(
            objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr,
            false,
            false
        )
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
        showDialog(
            objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,
            false,
            false
        )
    }

    override fun onCreatorPrfileListApiFailure(objCreatorListRes: ObjCreatorListRes) {
        showDialog(
           objCreatorListRes.profileResponse.responseStatusHeader.statusDescription,
            false,
            false
        )
    }

    override fun onUserNotConnected() {
        TODO("Not yet implemented")
    }


    override fun onResume() {
        super.onResume()

    }


    private fun getCityList() {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        viewModel.callCityListApi(language)

    }

    fun getProfileList() {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        viewModel.callProfileListApi(language)
    }

    private fun callSubAreaList(talukaId: String) {
        var language = PreferenceManger.get<String>(Constants.SELECTED_LANGUAGE)
        objSubAreaReq = objSubAreaReq.copy(talukaId, language!!)
        viewModel.callSubAreaList(objSubAreaReq)
    }


    private fun observeCityList() {
        viewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            val adapter: ArrayList<ObjTalukaDataList> = cityList
            findProfileFragmentBinding.autoCompleteCity.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )

            findProfileFragmentBinding.autoCompleteCity.setText(adapter.get(0).taluka);
            selectetdTalukaID = adapter.get(0).talukaId
            viewModel.city.value = adapter.get(0)
            findProfileFragmentBinding.tilcity.helperText = ""
            FindProfileBindingAdapter.isValidCity = true
        }


    }
    private fun observeProfileList() {
        viewModel.profileList.observe(viewLifecycleOwner) { profileList ->
            val adapter: List<ProfileDaum> = profileList
            findProfileFragmentBinding.autoProfileList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )

            findProfileFragmentBinding.autoProfileList.setText(adapter.get(0).profileName);
            selectetdProfileID = adapter.get(0).profileId
            viewModel.profile.value = adapter.get(0)
            findProfileFragmentBinding.tilprofile.helperText = ""
            FindProfileBindingAdapter.isProfile = true
        }


    }

    private fun observeCity() {
        viewModel.city.observe(viewLifecycleOwner) { city ->
            if (city != null) {
                callSubAreaList(selectetdTalukaID!!)
            }
        }
    }

    private fun observeSubAreaList() {
        viewModel.subAreaList.observe(viewLifecycleOwner) { subAreaList ->
            val adapter: ArrayList<ObjCityAreaData> = subAreaList
            findProfileFragmentBinding.autoCompleteAreaList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }

    override fun onClickBack() {
    }

    override fun onClickMenu() {
    }

    override fun onClickNotification() {
    }

    override fun onSubmitBtnClick() {
        findNavController().navigate(R.id.action_findProfileFragment_to_creatorListFragment)
    }

    fun onShowStateDropDown(view: View) {
        (view as AutoCompleteTextView).showDropDown()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == findProfileFragmentBinding.autoCompleteCity) {
            view.let {
                onShowStateDropDown(it)
            }
        } else if (view == findProfileFragmentBinding.autoCompleteAreaList) {
            onShowStateDropDown(view)
        }
        else if (view == findProfileFragmentBinding.autoProfileList) {
            onShowStateDropDown(view)
        }
        return true
    }
}