package com.vastu.realestate.appModule.employee.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.enquiry.employee.callbacks.response.IGetEmpDetailsResponse
import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponseMain
import com.vastu.enquiry.employee.repository.EmployeeDetailReqRepository
import com.vastu.realestate.appModule.employee.uiListener.IEmpDetailsViewListener
import com.vastu.realestate.utils.ApiUrlEndPoints

class EmployeeDetailsViewModel(application: Application):AndroidViewModel(application),IGetEmpDetailsResponse {
     var mContext:Application
    init {
        mContext = application
    }
    lateinit var iEmpDetailsViewListener: IEmpDetailsViewListener
    var employeeId = ObservableField("")
    var employeeName = ObservableField("")
    var taluka = ObservableField("")
    var subArea = ObservableField("")
    var mobile = ObservableField("")
    var email = ObservableField("")
    var address = ObservableField("")
    var rating = ObservableField("0")
    fun getEmpoyeeDetails(employeeId:String)
    {
        EmployeeDetailReqRepository.getEmpDetails(mContext,employeeId,ApiUrlEndPoints.GET_EMPLOYEE_DETAILS,this)
    }

    override fun onEmpDetailsSuccessResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain) {
        iEmpDetailsViewListener.onEmployeeSuccessResponse(objEmpDetailsResponseMain)
    }

    override fun onEmpDetailsFailureResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain) {
        iEmpDetailsViewListener.onEmployeeFailureResponse(objEmpDetailsResponseMain.employeeDetailsResponse)
    }
}