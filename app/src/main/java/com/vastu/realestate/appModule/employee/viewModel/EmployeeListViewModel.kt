package com.vastu.realestate.appModule.employee.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.enquiry.employee.callbacks.response.IGetEmpDetailsResponse
import com.vastu.enquiry.employee.callbacks.response.IGetEmpListResponse
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeListResponse
import com.vastu.enquiry.employee.model.employeeDetails.ObjEmpDetailsResponseMain
import com.vastu.enquiry.employee.repository.EmployeeDetailReqRepository
import com.vastu.enquiry.employee.repository.EmployeeListRepository
import com.vastu.realestate.appModule.employee.uiListener.IEmpListViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class EmployeeListViewModel(application: Application):AndroidViewModel(application),
    IGetEmpListResponse, IGetEmpDetailsResponse {
    var mContext:Application
    init {
        mContext=application
    }
    lateinit var iEmpListViewListener : IEmpListViewListener
    var employeeList = MutableLiveData<ArrayList<ObjEmployeeData>>()
    fun callEmployeeListApi(){
        EmployeeListRepository.getEmployeeList(mContext, ApiUrlEndPoints.GET_EMPLOYEE_LIST,this)
    }

    fun getEmployeeDetails(empId:String){
        EmployeeDetailReqRepository.getEmpDetails(mContext,empId,ApiUrlEndPoints.GET_EMPLOYEE_DETAILS,this)
    }
    override fun onEmpListSuccessResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        employeeList.value = objEmployeeListResponse.objGetEmployeeResponse!!.EmployeeData
    }

    override fun onEmpListFailureResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        iEmpListViewListener.onEmpListFailure(objEmployeeListResponse.objEmployeeResponse!!)
    }

    override fun networkFailure() {
    }

    override fun onEmpDetailsSuccessResponse(empDetailsResponse: ObjEmpDetailsResponseMain) {
        TODO("Not yet implemented")
    }

    override fun onEmpDetailsFailureResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain) {
        TODO("Not yet implemented")
    }


}