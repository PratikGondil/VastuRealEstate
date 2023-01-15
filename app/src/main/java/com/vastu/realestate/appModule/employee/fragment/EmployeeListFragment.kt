package com.vastu.realestate.appModule.employee.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.filter.FilterTypeAdapter
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.employee.adapter.EmployeeListAdapter
import com.vastu.realestate.appModule.employee.uiListener.IEmpListViewListener
import com.vastu.realestate.appModule.employee.viewModel.EmployeeListViewModel
import com.vastu.realestate.appModule.enquirylist.adapter.LoanEnquiryAdapter
import com.vastu.realestate.databinding.EmployeeListFragmentBinding
import com.vastu.realestate.utils.BaseConstant

class EmployeeListFragment:BaseFragment(),IToolbarListener, IEmpListViewListener {
    lateinit var employeeListViewModel: EmployeeListViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var empListFragmentBinding: EmployeeListFragmentBinding
    lateinit var empListRecyclerView:RecyclerView
    lateinit var empListAdapter:EmployeeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeListViewModel = ViewModelProvider(this)[EmployeeListViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        empListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.employee_list_fragment,container,false)
        empListFragmentBinding.employeeListViewModel = employeeListViewModel
        drawerViewModel.iToolbarListener = this
        empListFragmentBinding.drawerViewModel= drawerViewModel
        employeeListViewModel.iEmpListViewListener = this
        initView()
        return empListFragmentBinding.root
    }

    fun initView(){
        drawerViewModel.toolbarTitle.set(getString(R.string.employees))
        drawerViewModel.isDashBoard.set(false)
        callEmployeeList()
        observeEmpList()


    }
    fun callEmployeeList(){
        employeeListViewModel.callEmployeeListApi()
    }
    fun observeEmpList(){
        employeeListViewModel.employeeList.observe(viewLifecycleOwner) { empList ->
        if(empList != null){
            empListRecyclerView =empListFragmentBinding.rvEmpList
            empListAdapter = EmployeeListAdapter(empList,this)
            val layoutManager = LinearLayoutManager(requireContext())
            empListRecyclerView.layoutManager = layoutManager
            empListRecyclerView.adapter = empListAdapter

        }

        }

    }
    override fun onClickBack() {
        findNavController().navigate(R.id.action_EmployeeListFragment_to_VastuDashboardFragment)
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onEmpSelected(objEmployeeData: ObjEmployeeData) {
        findNavController().navigate(R.id.action_EmployeeListFragment_to_EmployeeDetailsFragment,
            bundleOf(BaseConstant.EMPLOYEE_ID to objEmployeeData.empId))
    }

    override fun onEmpListFailure(objEmployeeResponse: ObjEmployeeResponse) {
        TODO("Not yet implemented")
    }
}