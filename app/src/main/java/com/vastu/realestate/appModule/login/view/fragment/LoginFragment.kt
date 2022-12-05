package com.vastu.realestate.appModule.login.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.databinding.LoginFragmentBinding
import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts
import com.vastu.realestate.utils.BaseConstant

class LoginFragment : Fragment(), ILoginViewListener {

    lateinit var viewModel: LoginViewModel
    lateinit var binder: LoginFragmentBinding
    lateinit var objRegisterDlts:ObjRegisterDlts
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binder = DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        binder.lifecycleOwner = this
        binder.loginViewModel = viewModel
        viewModel.iLoginViewListener = this
//        initView()
        return binder.root
    }


    override fun onSendOtpClick() {
        viewModel.callLoginApi(viewModel.mobileNumber.get().toString())
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment)
//        if(!viewModel.mobileNumber.get().isNullOrEmpty()){
//            setOtpView()
//        }
    }
    override fun launchOtpScreen(objLoginResponse: ObjLoginResponse) {
        val bundle = Bundle()
        objRegisterDlts = objRegisterDlts.copy(userId = "1")
        bundle.putSerializable(BaseConstant.REGISTER_DTLS_OBJ, objRegisterDlts)
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
    }
}