package com.vastu.realestate.appModule.enquirylist.view

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsData
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsData
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusData
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request.ObjLoanStatusUpdateReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request.ObjPropStatusUpdateReq
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadViewListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.AssignLeadsViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.AssignLeadsFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger

class UpdateStatusFragment(var listerner: IAssignLeadListener): BottomSheetDialogFragment(), View.OnTouchListener,IAssignLeadViewListener
     {
         lateinit var assignLeadsBinding : AssignLeadsFragmentBinding
         lateinit var assignLeadsViewModel: AssignLeadsViewModel
         private lateinit var customProgressDialog : CustomProgressDialog
         lateinit var loanData :ObjEmpEnquiryDetailsData
         lateinit var propertyData: ObjEmpPropertyEnquiryDtlsData
         var usertype :String = ""
         var objLoanStatusUpdateReq =  ObjLoanStatusUpdateReq()
         var objPropStatusUpdateReq =  ObjPropStatusUpdateReq()
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
             initView()
             return assignLeadsBinding.root
         }

         private fun initView() {
             assignLeadsBinding.edtRemark.addTextChangedListener(object : TextWatcher {
                 override fun afterTextChanged(s: Editable?) {
                 }

                 override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                 }

                 override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                     assignLeadsViewModel.remark.set(s.toString())
                 }
             })
         }

         fun getBundleData(){
             try {
                 val args = arguments
                 customProgressDialog.show(requireContext())
                 if(args!=null) {
                     if(args.getSerializable(BaseConstant.ASSIGNED_LOAN_DATA)!=null) {
                         loanData =
                             args.getSerializable(BaseConstant.ASSIGNED_LOAN_DATA) as ObjEmpEnquiryDetailsData

                     }
                     else if(args.getSerializable(BaseConstant.ASSIGNED_PROPERTY_DATA)!=null) {
                         propertyData =
                             args.getSerializable(BaseConstant.ASSIGNED_PROPERTY_DATA) as ObjEmpPropertyEnquiryDtlsData

                     }
                     usertype = args.getString(BaseConstant.USER_TYPE).toString()

                 }

                 checkUserTypeToCtn()
             }catch (e:Exception){
                 e.printStackTrace()
             }

         }
         fun checkUserTypeToCtn(){
                 getEnquiryStatusList()
                 assignLeadsBinding.autoCompEmpName.setOnTouchListener(this)
                 assignLeadsViewModel.title.set(requireContext().resources.getString(R.string.update_status))
                 assignLeadsViewModel.btnText.set(requireContext().resources.getString(R.string.update_status))

         }
         fun  getEnquiryStatusList(){
             assignLeadsViewModel.callEnquiryStatusList()

         }
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        if (view == assignLeadsBinding.autoCompEmpName) {
            view.let {
                if (it != null) {
                    onShowStateDropDown(it)
                }
            }
        }
        return true
    }
         fun onShowStateDropDown(view: View){
             (view as AutoCompleteTextView).showDropDown()
         }
         override fun proceedToNext() {
             val userDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
             if(this::loanData.isInitialized){
                 objLoanStatusUpdateReq = objLoanStatusUpdateReq.copy(emp_id = userDetails.userId, loan_enq_id = loanData.loanEnqId,
                     status = (assignLeadsViewModel.status.value as ObjEnquiryStatusData).statusId,
                     remark = assignLeadsViewModel.remark.get()
                     )
                 assignLeadsViewModel.updateLoanEnqStatus(objLoanStatusUpdateReq)
             }
             else{
                 objPropStatusUpdateReq = objPropStatusUpdateReq.copy(emp_id = userDetails.userId,
                     pro_enq_id =propertyData.proEnqId, status = (assignLeadsViewModel.status.value as ObjEnquiryStatusData).statusId,
                     remark = assignLeadsViewModel.remark.get()
                     )
                 updateEnquiryStatus(objPropStatusUpdateReq)

             }

         }

         override fun callAssignApi() {

         }

         override fun onEmpListFailure(error: String) {
             listerner.onEmpListFailure(error,false,false)
             this.dismiss()
         }

         override fun onLeadAssignSuccess() {
         }

         override fun updateEnquiryStatusSuccess() {
             listerner.onAssignLeadSuccess()
             this.dismiss()
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
                 if (loanData.statusName?.isNotEmpty() == true){
                 assignLeadsBinding.autoCompEmpName.setText(
                     assignLeadsBinding.autoCompEmpName.adapter.getItem(
                         BaseUtils.getPreviousStatus(loanData.statusName!!, statusList)
                     ).toString(), false
                 )
                 val index = BaseUtils.getPreviousStatus(
                     loanData.statusName!!,
                     statusList
                 )

                 setPreselectedPaymentReason(index)
                     }
             }
             else{
                 if (propertyData.status_name?.isNotEmpty() == true) {
                     assignLeadsBinding.autoCompEmpName.setText(
                         assignLeadsBinding.autoCompEmpName.adapter.getItem(
                             BaseUtils.getPreviousStatus(propertyData.status_name!!, statusList)
                         ).toString(), false
                     )
                     val index = BaseUtils.getPreviousStatus(
                         propertyData.status_name!!,
                         statusList
                     )
                     setPreselectedPaymentReason(index)
                 }
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
         fun updateEnquiryStatus(objPropStatusUpdateReq: ObjPropStatusUpdateReq){
             assignLeadsViewModel.updatePropEnqStatus(objPropStatusUpdateReq)
         }
         override fun closeAssignLead(){
             this.dismiss()
         }
     }