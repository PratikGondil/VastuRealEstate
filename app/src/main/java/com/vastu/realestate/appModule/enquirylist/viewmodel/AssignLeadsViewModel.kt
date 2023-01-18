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
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse
import com.vastu.enquiry.loan.repository.AssignLoanEnquiryRepository
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq
import com.vastu.enquiry.property.repository.AssignPropertyEnquiryRepository
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class AssignLeadsViewModel(application: Application):AndroidViewModel(application),
    IAssignLeadResponse,IGetEmpListResponse {
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
   fun  callAssignLoanLeadApi(objAssignLoanEnquiryReq: ObjAssignLoanEnquiryReq) {
       AssignLoanEnquiryRepository.assignLoanLead(mContext,ApiUrlEndPoints.ASSIGN_LOAN_ENQUIRY,objAssignLoanEnquiryReq,this)
   }
    fun callAssignPropertyLeadApi(objAssignPropertyEnquiryReq: ObjAssignPropertyEnquiryReq){
        AssignPropertyEnquiryRepository.assignPropertyLead(mContext,ApiUrlEndPoints.ASSIGN_PROPERTY_ENQUIRY,objAssignPropertyEnquiryReq,this)
    }
    override fun onEmpListSuccessResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        employeeList.value = objEmployeeListResponse.objGetEmployeeResponse!!.EmployeeData
    }

    override fun onEmpListFailureResponse(objEmployeeListResponse: ObjEmployeeListResponse) {
        iAssignLeadViewListener.onEmpListFailure(objEmployeeListResponse.objEmployeeResponse?.ResponseStatusHeader!!.statusDescription.toString())
    }

    override fun networkFailure() {
    }
    fun assignLeadToEmp(){
        iAssignLeadViewListener.callAssignApi()
    }

    override fun onSuccessAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse) {
    iAssignLeadViewListener.onLeadAssignSuccess()
    }

    override fun onFailureAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse) {
        iAssignLeadViewListener.onEmpListFailure(objAssignEnquiryReponse.objPropertyEmpAssignResponse?.objResponseStatusHeader?.statusDescription.toString())
    }
}