package com.vastu.realestate.appModule.dashboard.bindingAdapter

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanInformationViewModel

object LoanInfoBindingAdapter {
    @JvmStatic
    @BindingAdapter("onClickBtn")
    fun AppCompatButton.onClickButton(loanInformationViewModel: LoanInformationViewModel){
        setOnClickListener {
            if (loanInformationViewModel.btnText.get().equals(context.getString(R.string.viewMore),true))
            {
                loanInformationViewModel.maxline.set(20)
                loanInformationViewModel.btnText.set(context.getString(R.string.viewLess))
                loanInformationViewModel.mainMorebtn.set(context.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))
            }
            else
            {
                loanInformationViewModel.maxline.set(16)
                loanInformationViewModel.btnText.set(context.getString(R.string.viewMore))
                loanInformationViewModel.mainMorebtn.set(context.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
            }
        }
    }
}