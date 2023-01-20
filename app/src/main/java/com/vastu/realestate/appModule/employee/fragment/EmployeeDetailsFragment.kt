package com.vastu.realestate.appModule.employee.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponse
import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponseMain
import com.vastu.enquiry.employee.model.response.employeeDetails.ObjGetEmpDetailsResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userType
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.employee.adapter.EmployeeListAdapter
import com.vastu.realestate.appModule.employee.uiListener.IEmpDetailsViewListener
import com.vastu.realestate.appModule.employee.viewModel.EmployeeDetailsViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.EmployeeDetailsFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger

class EmployeeDetailsFragment:BaseFragment(),IToolbarListener, IEmpDetailsViewListener {
    lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var empDetailsFragmentBinding: EmployeeDetailsFragmentBinding
    var empListAdapter: EmployeeListAdapter? =null
    var objVerifyDtls:ObjVerifyDtls? = null
    lateinit var employeeId:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeDetailsViewModel = ViewModelProvider(this)[EmployeeDetailsViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        empDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.employee_details_fragment,container,false)
        empDetailsFragmentBinding.empDetailsViewModel = employeeDetailsViewModel
        drawerViewModel.iToolbarListener = this
        empDetailsFragmentBinding.drawerViewModel= drawerViewModel
        employeeDetailsViewModel.iEmpDetailsViewListener = this
        initView()
        return empDetailsFragmentBinding.root
    }

    override fun initView(){
        when(userType){
            BaseConstant.EMPLOYEES,BaseConstant.CUSTOMER->
                drawerViewModel.toolbarTitle.set(getString(R.string.profile))
            BaseConstant.BUILDER->
            drawerViewModel.toolbarTitle.set(getString(R.string.employees))
        }


        drawerViewModel.isDashBoard.set(false)
        getBundleData()

    }
    override fun getBundleData(){
        val args = arguments
        if (args != null) {
            if (args.getString(BaseConstant.EMPLOYEE_ID)!= null) {
                employeeId = args.getString(BaseConstant.EMPLOYEE_ID).toString()
                callEmployeeDetails()

            }
        }
        else{
            if(userType.equals(BaseConstant.CUSTOMER)|| userType.equals(BaseConstant.EMPLOYEES)){
                objVerifyDtls = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
                setCutomerView(objVerifyDtls!!)
            }
        }
    }

    override fun callEmployeeDetails(){
        employeeDetailsViewModel.getEmpoyeeDetails(employeeId)
    }

    override fun onEmployeeSuccessResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain) {
        setEmpView(objEmpDetailsResponseMain.GetEmployeeDetailsResponse!!)
    }
    fun setCutomerView(objVerifyDtls:ObjVerifyDtls){
        employeeDetailsViewModel.employeeId.set(objVerifyDtls.userId)
        employeeDetailsViewModel.employeeName.set(objVerifyDtls.firstName)
        employeeDetailsViewModel.taluka.set(objVerifyDtls.city)
        employeeDetailsViewModel.mobile.set(objVerifyDtls.mobileNo)
        employeeDetailsViewModel.email.set(objVerifyDtls.emailId)
        empDetailsFragmentBinding.tilSubArea.visibility = View.GONE
        empDetailsFragmentBinding.tilAddress.visibility = View.GONE
        empDetailsFragmentBinding.tilCity.visibility = View.GONE
//        if(objVerifyDtls.EmployeeDetailsData[0].rating?.isNotEmpty() == true)
//            employeeDetailsViewModel.rating.set(objVerifyDtls.EmployeeDetailsData[0].rating)
    }
    fun setEmpView(employeeDetails: ObjGetEmpDetailsResponse){
        employeeDetailsViewModel.employeeId.set(employeeDetails.EmployeeDetailsData[0].empId)
        employeeDetailsViewModel.employeeName.set(employeeDetails.EmployeeDetailsData[0].empName)
        employeeDetailsViewModel.taluka.set(employeeDetails.EmployeeDetailsData[0].taluka)
        employeeDetailsViewModel.subArea.set(employeeDetails.EmployeeDetailsData[0].subArea)
        employeeDetailsViewModel.mobile.set(employeeDetails.EmployeeDetailsData[0].mobile)
        employeeDetailsViewModel.email.set(employeeDetails.EmployeeDetailsData[0].email)
        employeeDetailsViewModel.address.set(employeeDetails.EmployeeDetailsData[0].address)

        if(employeeDetails.EmployeeDetailsData[0].rating?.isNotEmpty() == true)
        employeeDetailsViewModel.rating.set(employeeDetails.EmployeeDetailsData[0].rating)
    }
    override fun onEmployeeFailureResponse(employeeDetailsResponse: ObjEmpDetailsResponse?) {
        showDialog(employeeDetailsResponse!!.ResponseStatusHeader!!.statusDescription!!,false,false)

    }
    override fun onClickBack() {
        when(userType){
            BaseConstant.EMPLOYEES,BaseConstant.CUSTOMER->
                findNavController().navigate(R.id.action_employeeDetailsFragment_to_vastuDashboardFragment)
            BaseConstant.BUILDER->
            findNavController().navigate(R.id.action_EmployeeDetailsFragment_to_EmployeeListFragment)
        }

    }

    override fun onClickMenu() {

    }

    override fun onClickNotification() {

    }
}