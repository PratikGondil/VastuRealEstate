package com.vastu.realestate.appModule.enquirylist.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusData
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request.ObjLoanStatusUpdateReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request.ObjPropStatusUpdateReq
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.AssignLeadsViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.AssignLeadsFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceKEYS.USER
import com.vastu.realestate.utils.PreferenceManger

class AssignLeadsFragment(var listerner: IAssignLeadListener): BottomSheetDialogFragment(),View.OnTouchListener,
    IAssignLeadViewListener {
    lateinit var assignLeadsBinding :AssignLeadsFragmentBinding
    lateinit var assignLeadsViewModel: AssignLeadsViewModel
    lateinit var loanData :LoanData
    lateinit var propertyData:EnquiryData
    private lateinit var customProgressDialog : CustomProgressDialog
     var objLoanStatusUpdateReq =  ObjLoanStatusUpdateReq()
    var objPropStatusUpdateReq = ObjPropStatusUpdateReq()
    var objAssignLoanEnquiryReq = ObjAssignLoanEnquiryReq()
    var objAssignPropertyEnquiryReq = ObjAssignPropertyEnquiryReq()
    var usertype :String = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setHeight(it)
                behaviour.expandedOffset = 50
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }
    private fun setHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        var dMetrics = resources.getDisplayMetrics()
        val h = Math.round(dMetrics.heightPixels / dMetrics.density)
        layoutParams.height = h*2

        bottomSheet.layoutParams = layoutParams

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        assignLeadsViewModel = ViewModelProvider(this)[AssignLeadsViewModel::class.java]
        assignLeadsBinding = DataBindingUtil.inflate(inflater, R.layout.assign_leads_fragment,container,false)
//        tabLayout = multipleFiltersBinding.filterType
        assignLeadsBinding.lifecycleOwner = this
        assignLeadsBinding.assignLeadsViewModel = assignLeadsViewModel
        assignLeadsViewModel.iAssignLeadViewListener = this
        customProgressDialog = CustomProgressDialog.getInstance()

        getBundleData()
        observeEmpList()
        return assignLeadsBinding.root
    }
    fun getBundleData(){
        try {
            val args = arguments
            customProgressDialog.show(requireContext())
            if(args!=null) {
                if(args.getSerializable(BaseConstant.LOAN_DATA)!=null) {
                    loanData =
                        args.getSerializable(BaseConstant.LOAN_DATA) as LoanData

                }
                else if(args.getSerializable(BaseConstant.PROPERTY_DATA)!=null) {
                    propertyData =
                        args.getSerializable(BaseConstant.PROPERTY_DATA) as EnquiryData

                }
                usertype = args.getString(BaseConstant.USER_TYPE).toString()

            }

            checkUserTypeToCtn()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun checkUserTypeToCtn(){
        if(usertype.equals(BaseConstant.ADMIN))
            getEmployeeList()
        else {
            getEnquiryStatusList()
            assignLeadsBinding.autoCompEmpName.setOnTouchListener(this)
            assignLeadsViewModel.title.set(requireContext().resources.getString(R.string.update_status))
            assignLeadsViewModel.btnText.set(requireContext().resources.getString(R.string.update_status))
        }
    }

    fun getEmployeeList(){

//        assignLeadsBinding.autoCompEmpName.setOnTouchListener(this)
        assignLeadsViewModel.callEmployeeListApi()
    }

    fun observeEmpList(){
        assignLeadsViewModel.employeeList.observe(viewLifecycleOwner) { empList ->

            val adapter: ArrayList<ObjEmployeeData> = empList
            assignLeadsBinding.autoCompEmpName.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item, adapter
                )
            )
            getEnquiryStatusList()
        }

    }
override fun proceedToNext(){
    if (usertype.equals(BaseConstant.ADMIN))
        callAssignApi()
    else{
        val userDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
        if(this::loanData.isInitialized){
            objLoanStatusUpdateReq = objLoanStatusUpdateReq.copy(emp_id = userDetails.userId, loan_enq_id = loanData.loanId,
                status = (assignLeadsViewModel.status.value as ObjEnquiryStatusData).statusId)
            assignLeadsViewModel.updateLoanEnqStatus(objLoanStatusUpdateReq)
        }
        else{
            objPropStatusUpdateReq = objPropStatusUpdateReq.copy(emp_id = userDetails.userId,
                pro_enq_id =propertyData.propertyId, status = (assignLeadsViewModel.status.value as ObjEnquiryStatusData).statusId)
            updateEnquiryStatus(objPropStatusUpdateReq)

        }

    }

}
    override fun callAssignApi() {
        if(this::loanData.isInitialized){
            objAssignLoanEnquiryReq = objAssignLoanEnquiryReq.copy(loan_enq_id = loanData.loanId,
                emp_id = (assignLeadsViewModel.empName.value as ObjEmployeeData).empId)
            assignLeadsViewModel.callAssignLoanLeadApi(objAssignLoanEnquiryReq)
        }
        else  if(this::propertyData.isInitialized){
            objAssignPropertyEnquiryReq = objAssignPropertyEnquiryReq.copy( property_enq_id= propertyData.propertyId,
                emp_id = (assignLeadsViewModel.empName.value as ObjEmployeeData).empId)
            assignLeadsViewModel.callAssignPropertyLeadApi(objAssignPropertyEnquiryReq)
        }
    }
    override fun onEmpListFailure(error: String){
        listerner.onEmpListFailure(error,false,false)
        this.dismiss()

    }
    override fun onLeadAssignSuccess(){
        if(this::loanData.isInitialized){
            objLoanStatusUpdateReq = objLoanStatusUpdateReq.copy(emp_id = objAssignLoanEnquiryReq.emp_id, loan_enq_id = objAssignLoanEnquiryReq.loan_enq_id,
            status = BaseConstant.TO_DO)
            assignLeadsViewModel.updateLoanEnqStatus(objLoanStatusUpdateReq)
        }
        else{
            objPropStatusUpdateReq = objPropStatusUpdateReq.copy(emp_id = objAssignPropertyEnquiryReq.emp_id,
            pro_enq_id = objAssignPropertyEnquiryReq.property_enq_id, status = BaseConstant.TO_DO)
            updateEnquiryStatus(objPropStatusUpdateReq)
        }

    }

    override fun updateEnquiryStatusSuccess() {
        listerner.onAssignLeadSuccess()
        this.dismiss()
    }

    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == assignLeadsBinding.autoCompEmpName) {
            view.let {
                onShowStateDropDown(it)
            }
        }
        return true
    }
    fun onShowStateDropDown(view: View){
        (view as AutoCompleteTextView).showDropDown()
    }

    override fun onGetEnquirySuccessResponse(objEnquiryStatusResponseMain: ObjEnquiryStatusResponseMain) {
        if (usertype.equals(BaseConstant.EMPLOYEES)) {
            setEnqStatusListToAdapter(objEnquiryStatusResponseMain.objGetEnquiryStatusDetailsResponse.objEnquiryStatusData)
            assignLeadsViewModel.isAssignLead.set(false)
        }
        customProgressDialog.dismiss()

    }
    fun setEnqStatusListToAdapter(statusList: ArrayList<ObjEnquiryStatusData>) {
//        val adapter: ArrayList<ObjEnquiryStatusData> = statusList
        assignLeadsBinding.autoCompEmpName.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.drop_down_item, statusList
            )
        )
        if(this::loanData.isInitialized) {
            assignLeadsBinding.autoCompEmpName.setText(
                assignLeadsBinding.autoCompEmpName.adapter.getItem(
                    BaseUtils.getPreviousStatus(loanData.status!!, statusList)
                ).toString(), false
            )
            val index = BaseUtils.getPreviousStatus(
                loanData.status!!,
                statusList
            )
            setPreselectedPaymentReason(index)
        }
        else{
            assignLeadsBinding.autoCompEmpName.setText(
                assignLeadsBinding.autoCompEmpName.adapter.getItem(
                    BaseUtils.getPreviousStatus(propertyData.status!!, statusList)
                ).toString(), false
            )
            val index = BaseUtils.getPreviousStatus(
                propertyData.status!!,
                statusList
            )
            setPreselectedPaymentReason(index)
        }
    }

    fun setPreselectedPaymentReason(index: Int) {
        try {
            assignLeadsViewModel.status.value = (
                assignLeadsBinding.autoCompEmpName.adapter!!.getItem(
                    index
                ) as ObjEnquiryStatusData
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun  getEnquiryStatusList(){
        assignLeadsViewModel.callEnquiryStatusList()

    }
    fun updateEnquiryStatus(objPropStatusUpdateReq:ObjPropStatusUpdateReq){
        assignLeadsViewModel.updatePropEnqStatus(objPropStatusUpdateReq)
    }
    override fun closeAssignLead(){
        this.dismiss()
    }

//    fun updateLoanStatus(objLoanStatusUpdateReq: ObjLoanStatusUpdateReq) {
//        assignLeadsViewModel.updateLoanEnqStatus(objLoanStatusUpdateReq)
//    }
    }