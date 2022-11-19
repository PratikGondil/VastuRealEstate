package com.vastu.realestate.appModule.login.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.databinding.LoginFragmentBinding

class LoginFragment : Fragment(), ILoginViewListener {

    lateinit var viewModel: LoginViewModel
    lateinit var binder: LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        binder.lifecycleOwner = this
        binder.loginViewModel = viewModel
        viewModel.iLoginViewListener = this
        initView()
        return binder.root
    }

    fun initView(){
        viewModel.title.set(resources.getString(R.string.sign_in_text))
        viewModel.isVisibleOtpLayout.set(View.GONE)
        viewModel.isVisibleSignupTextLayout.set(View.VISIBLE)
        viewModel.isInputLayoutVisible.set(View.VISIBLE)
    }

    override fun onNextBtnClick() {
        if(!viewModel.mobileNumber.get().isNullOrEmpty()){
            setOtpView()
        }
    }
    fun setOtpView(){
        viewModel.title.set(resources.getString(R.string.verify_your_mobile_number))
        viewModel.isVisibleOtpLayout.set(View.VISIBLE)
        viewModel.isVisibleSignupTextLayout.set(View.GONE)
        viewModel.isInputLayoutVisible.set(View.GONE)
        viewModel.otpSentNumber.set(resources.getString(R.string.enter_otp_text)+viewModel.mobileNumber.get())
    }

}