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
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.employee.adapter.EmployeeListAdapter
import com.vastu.realestate.appModule.employee.uiListener.IEmpDetailsViewListener
import com.vastu.realestate.appModule.employee.viewModel.EmployeeDetailsViewModel
import com.vastu.realestate.databinding.EmployeeDetailsFragmentBinding
import com.vastu.realestate.utils.BaseConstant

class EmployeeDetailsFragment:BaseFragment(),IToolbarListener, IEmpDetailsViewListener {
    lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var empDetailsFragmentBinding: EmployeeDetailsFragmentBinding
    var empListAdapter: EmployeeListAdapter? =null
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
        drawerViewModel.toolbarTitle.set(getString(R.string.employees))
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
    }

    override fun callEmployeeDetails(){
        employeeDetailsViewModel.getEmpoyeeDetails(employeeId)
    }

    override fun onEmployeeSuccessResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain) {
        setView(objEmpDetailsResponseMain.GetEmployeeDetailsResponse!!)
    }

    fun setView(employeeDetails: ObjGetEmpDetailsResponse){
        employeeDetailsViewModel.employeeId.set(employeeDetails.EmployeeDetailsData[0].empId)
        employeeDetailsViewModel.employeeName.set(employeeDetails.EmployeeDetailsData[0].empName)
        employeeDetailsViewModel.taluka.set(employeeDetails.EmployeeDetailsData[0].taluka)
        employeeDetailsViewModel.subArea.set(employeeDetails.EmployeeDetailsData[0].subArea)
        employeeDetailsViewModel.mobile.set(employeeDetails.EmployeeDetailsData[0].mobile)
        employeeDetailsViewModel.email.set(employeeDetails.EmployeeDetailsData[0].email)
        employeeDetailsViewModel.address.set(employeeDetails.EmployeeDetailsData[0].address)
//        employeeDetailsViewModel.rating.set(employeeDetails.EmployeeDetailsData[0].rating!!.toDouble())
    }
    override fun onEmployeeFailureResponse(employeeDetailsResponse: ObjEmpDetailsResponse?) {
        showDialog(employeeDetailsResponse!!.ResponseStatusHeader!!.statusDescription!!,false,false)

    }
    override fun onClickBack() {
       findNavController().navigate(R.id.action_EmployeeDetailsFragment_to_EmployeeListFragment)
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }
}