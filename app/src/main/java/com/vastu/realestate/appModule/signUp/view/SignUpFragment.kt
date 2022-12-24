package com.vastu.realestate.appModule.signUp.view

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.vastu.realestate.R
import com.vastu.realestate.appModule.signUp.uiInterfaces.ISignUpViewListener
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.SignUpFragmentBinding
import com.vastu.realestate.registrationcore.model.request.ObjSubAreaReq
import com.vastu.realestate.registrationcore.model.request.ObjUserInfo
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterDlts
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponseMain
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaResponseMain
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.registrationcore.model.response.subArea.ObjGetCityAreaDetailResponseMain
import com.vastu.realestate.utils.BaseConstant.REGISTER_DTLS_OBJ

class SignUpFragment : Fragment(),View.OnTouchListener, ISignUpViewListener {

    private lateinit var signUpViewModel: SignUpViewModel
    lateinit var signUpFragmentBinding: SignUpFragmentBinding
    lateinit var viewPager :ViewPager2
    var objUserInfo= ObjUserInfo()
    var objSubAreaReq = ObjSubAreaReq()
    lateinit var customProgressDialog : CustomProgressDialog
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
        initView()
        getCityList()
        observeCityList()
        observeCity()
        observeSubAreaList()
        customProgressDialog = CustomProgressDialog.getInstance()
        customProgressDialog.dismiss()
        return signUpFragmentBinding.root
    }

    fun initView(){
        signUpFragmentBinding.autoCompleteCity.setOnTouchListener(this)
        signUpFragmentBinding.autoCompleteAreaList.setOnTouchListener(this)
        val subAreaList = arrayOf("Nigdi","Kalewadi","Hinjewadi","Chinchwad","Kothrud")

    }
    fun observeCityList(){
        signUpViewModel.cityList.observe(viewLifecycleOwner) { cityList ->
//            val cityListAdapter =
//                arrayOf("pune", "PCMC","Mumbai", "Nagpur", "Thane", "Nashik", "Kalyan-Dombivli", "Aurangabad", "Jalgaon")

            val adapter: ArrayList<ObjTalukaDataList> =cityList

            signUpFragmentBinding.autoCompleteCity.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
    }
    fun observeCity(){
        signUpViewModel.city.observe(viewLifecycleOwner){city->
            if (city != null) {
                  callSubAreaList(city.talukaId!!)
                }

        }
    }
    fun observeSubAreaList(){
        signUpViewModel.subAreaList.observe(viewLifecycleOwner){subAreaList->
            val adapter: ArrayList<ObjCityAreaData> =subAreaList
            signUpFragmentBinding.autoCompleteAreaList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
        }
//        val subAreaListAdapter = arrayOf("Nigdi","Kalewadi","Hinjewadi","Chinchwad","Kotharud")
//        val adapter: ArrayAdapter<String> =
//            ArrayAdapter<String>(requireContext(), R.layout.drop_down_item, subAreaListAdapter)
//        signUpFragmentBinding.autoCompleteAreaList.setAdapter(
//            adapter
//        )
    }

    override fun registerUser(){
        customProgressDialog.show(requireContext())
        getUserInfo()
        signUpViewModel.callRegistrationApi(objUserInfo)
    }

    override fun launchOtpScreen(objRegisterDlts: ObjRegisterDlts) {
        customProgressDialog.show(requireContext())
        val bundle = Bundle()
        bundle.putSerializable(REGISTER_DTLS_OBJ, objRegisterDlts)
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
    }

    private fun getUserInfo(){
         objUserInfo =objUserInfo.copy(firstName = signUpViewModel.firstName.get()!!,
        middleName = signUpViewModel.middleName.get()!!,
        lastName = signUpViewModel.lastName.get()!!,
        mobile = signUpViewModel.mobileNumber.get()!!,
        city = signUpViewModel.city.value!!.taluka,
        subArea = signUpViewModel.subArea.get()!!.subArea!!,
        emailId = signUpViewModel.mailId.get()!!,
        userType = "2")


    }

    fun getCityList(){
//        customProgressDialog.show(requireContext())
        signUpViewModel.callCityListApi()

    }
    fun callSubAreaList(talukaId: String) {
        objSubAreaReq = ObjSubAreaReq().copy(talukaId = talukaId )
        signUpViewModel.callSubAreaList(objSubAreaReq)
    }
    override fun goToLogin() {
        customProgressDialog.dismiss()
            viewPager.currentItem = 0

    }

    override fun onRegistrationFail(objRegisterResponseMain: ObjRegisterResponseMain) {
        customProgressDialog.dismiss()
        Toast.makeText(requireContext(),objRegisterResponseMain.objRegisterResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()

    }

    override fun onCityListApiFailure(objTalukaResponseMain: ObjTalukaResponseMain) {
        customProgressDialog.dismiss()
        Toast.makeText(requireContext(),objTalukaResponseMain.objTalukaResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()
    }

    override fun onSubAreaListApiFailure(objGetCityAreaDetailResponseMain: ObjGetCityAreaDetailResponseMain) {
        customProgressDialog.dismiss()
        Toast.makeText(requireContext(),objGetCityAreaDetailResponseMain.objCityAreaResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()
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


}