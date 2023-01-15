package com.vastu.realestate.appModule.enquirylist.viewmodel

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vastu.enquiry.employee.callbacks.response.IGetEmpListResponse
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeListResponse
import com.vastu.enquiry.employee.repository.EmployeeListRepository
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class AssignLeadsViewModel(application: Application):AndroidViewModel(application),
    IGetEmpListResponse {
    lateinit var iAssignLeadViewListener: IAssignLeadViewListener
    var mContext:Application
    init {
        mContext=application
    }
    var empName =MutableLiveData<ObjEmployeeData?>()
    var employeeList = MutableLiveData<ArrayList<ObjEmployeeData>>()
    var isBtnEnable =ObservableField(false)
    var btnBackground = ObservableField(ContextCompat.getDrawable(mContext, R.drawable.button_inactive_background))

    fun callEmployeeListApi(){
        EmployeeListRepository.getEmployeeList(mContext,ApiUrlEndPoints.GET_EMPLOYEE_LIST,this)
    }

    override fun onEmpListSuccessResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        employeeList.value = objEmployeeListResponse.objGetEmployeeResponse!!.EmployeeData
    }

    override fun onEmpListFailureResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        iAssignLeadViewListener.onEmpListFailure(objEmployeeListResponse.objEmployeeResponse!!)
    }

    override fun networkFailure() {
        TODO("Not yet implemented")
    }
fun assignLeadToEmp(){
    iAssignLeadViewListener.callAssignApi()
}
}