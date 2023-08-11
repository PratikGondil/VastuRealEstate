package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanInfoListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanInformationViewModel
import com.vastu.realestate.databinding.LoanInfoFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import java.util.regex.Pattern


class LoanInformationFragment : BaseFragment(), ILoanInfoListener, IToolbarListener {
    private lateinit var loanInfoBinding: LoanInfoFragmentBinding
    private lateinit var loanInfoViewModel: LoanInformationViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    var loan = LoanInterstedData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        loanInfoViewModel = ViewModelProvider(this)[LoanInformationViewModel::class.java]
        loanInfoBinding = DataBindingUtil.inflate(inflater, R.layout.loan_info_fragment, container, false)
        loanInfoBinding.lifecycleOwner = this
        loanInfoViewModel.iLoanInfoListener = this
        drawerViewModel.iToolbarListener = this
        loanInfoBinding.loanInfoViewModel = loanInfoViewModel
        loanInfoBinding.drawerViewModel = drawerViewModel
       getBundleData()
        return loanInfoBinding.root
    }

    private fun getBundleData(){
        val args = this.arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.LOAN_DATA) != null) {
                loan =  args.getSerializable(BaseConstant.LOAN_DATA) as LoanInterstedData
            }
        }
        initView()
    }
    private fun initView() {
        drawerViewModel.toolbarTitle.set(loan.loanName)
        drawerViewModel.isDashBoard.set(false)
        loanInfoViewModel.maxline.set(16)
        loanInfoViewModel.btnText.set(context?.getString(R.string.viewMore))
        loanInfoViewModel.mainMorebtn.set(context?.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))

    }
    override fun onClickBack() {
//        activity?.onBackPressed()
        findNavController().navigateUp()
    }

    override fun onClickMenu() {
        //
    }

    override fun onClickNotification() {
        //
    }

    override fun fabAddLoanEnquiry() {
        val bundle = Bundle()
        bundle.putSerializable(BaseConstant.LOAN_DATA, loan)
        findNavController().navigate(R.id.addLoanEnquiryFragment, bundle)

    }


}