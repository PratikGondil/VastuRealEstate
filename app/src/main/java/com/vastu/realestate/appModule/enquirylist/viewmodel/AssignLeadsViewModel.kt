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
import com.vastu.enquiry.statusUpdate.enquiryStatus.callbacks.IGetEnquiryStatusResponse
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusData
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain
import com.vastu.enquiry.statusUpdate.enquiryStatus.repository.EnquiryStatusRequestRepository
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.callbacks.IUpdateLoanEnqStatusResposne
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request.ObjLoanStatusUpdateReq
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response.ObjLoanEnqStatusResponseMain
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.repository.UpdateLoanEnquiryStatus
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.callbacks.IUpdatePropEnqStatusResponse
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request.ObjPropStatusUpdateReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response.ObjPropEnqStatusResponseMain
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.repository.UpdatePropertyEnquiryStatus
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class AssignLeadsViewModel(application: Application):AndroidViewModel(application),
    IAssignLeadResponse,IGetEmpListResponse, IGetEnquiryStatusResponse,
    IUpdatePropEnqStatusResponse,
    IUpdateLoanEnqStatusResposne {
    lateinit var iAssignLeadViewListener: IAssignLeadViewListener
    var mContext:Application
    init {
        mContext=application
    }
    var title = ObservableField(mContext.getString(R.string.assign_lead_text))
    var isAssignLead = ObservableField(true)
    var btnText = ObservableField(mContext.getString(R.string.assign_lead_text))
    var empName =MutableLiveData<ObjEmployeeData?>()
    var status = MutableLiveData<ObjEnquiryStatusData>()
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
    fun callEnquiryStatusList(){
        EnquiryStatusRequestRepository.getEnquiryStatusList(mContext,ApiUrlEndPoints.ENQUIRY_STATUS,this)
    }

    fun updateLoanEnqStatus(objLoanStatusUpdateReq: ObjLoanStatusUpdateReq){
        UpdateLoanEnquiryStatus.updateLoanEnqStatus(mContext,objLoanStatusUpdateReq,ApiUrlEndPoints.UPDATE_LOAN_STATUS,this)
    }

    fun updatePropEnqStatus(objPropStatusUpdateReq: ObjPropStatusUpdateReq){
        UpdatePropertyEnquiryStatus.updatePropEnqStatus(mContext,objPropStatusUpdateReq,ApiUrlEndPoints.UPDATE_ENQUIRY_STATUS,this)
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
        iAssignLeadViewListener.proceedToNext()
    }

    override fun onSuccessAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse) {
    iAssignLeadViewListener.onLeadAssignSuccess()
    }

    override fun onFailureAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse) {
        iAssignLeadViewListener.onEmpListFailure(objAssignEnquiryReponse.objPropertyEmpAssignResponse?.objResponseStatusHeader?.statusDescription.toString())
    }

    override fun onGetEnquirySuccessResponse(objEnquiryStatusResponseMain: ObjEnquiryStatusResponseMain) {
        iAssignLeadViewListener.onGetEnquirySuccessResponse(objEnquiryStatusResponseMain)
    }

    override fun onGetEnquiryFailure(objEnquiryStatusResponseMain: ObjEnquiryStatusResponseMain) {
        iAssignLeadViewListener.onEmpListFailure(objEnquiryStatusResponseMain.objEnquiryStatusResponse.responseStatusHeader?.statusDescription!!)
    }

    override fun onUpdateLoanEnqStatusSuccess(objLoanEnqStatusResponseMain: ObjLoanEnqStatusResponseMain) {
        iAssignLeadViewListener.updateEnquiryStatusSuccess()
    }

    override fun onUpdateLoanEnqStatusFailure(objLoanEnqStatusResponseMain: ObjLoanEnqStatusResponseMain) {
        iAssignLeadViewListener.onEmpListFailure(objLoanEnqStatusResponseMain.statusLoanEmpAssignResponse!!.responseStatusHeader?.statusDescription!!)

    }

    override fun onUpdatePropEnqStatusSuccess(objPropEnqStatusResponseMain: ObjPropEnqStatusResponseMain) {
        iAssignLeadViewListener.updateEnquiryStatusSuccess()
    }

    override fun onUpdatePropEnqStatusFailure(objPropEnqStatusResponseMain: ObjPropEnqStatusResponseMain) {
        iAssignLeadViewListener.onEmpListFailure(objPropEnqStatusResponseMain.statusEmpAssignResponse!!.responseStatusHeader?.statusDescription!!)

    }
    fun dismiss(){
        iAssignLeadViewListener.closeAssignLead()
    }
}