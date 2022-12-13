package com.vastu.realestate.appModule.otp.fragment

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.otp.uiListener.IVerifyOtpViewListener
import com.vastu.realestate.appModule.otp.viewModel.OTPViewModel
import com.vastu.realestate.commoncore.model.otp.ObjUserData
import com.vastu.realestate.commoncore.model.otp.request.ObjVerifyOtpReq
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyOtpResponseMain
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.OtpFragmentBinding
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.utils.BaseConstant
import java.util.*

class OTPFragment : Fragment(), IVerifyOtpViewListener {


    private lateinit var viewModel: OTPViewModel
    lateinit var otpFragmentBinding: OtpFragmentBinding
     var objVerifyOtpReq = ObjVerifyOtpReq()
    var ratetimer: Timer? = null
    var updateSyncTimer: TimerTask? = null
    val handler = Handler()
    var otpCounter: Int = 30
    lateinit var customProgressDialog : CustomProgressDialog
    lateinit var objUserData: ObjUserData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(OTPViewModel::class.java)
        otpFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.otp_fragment, container, false)
        otpFragmentBinding.otpViewModel = viewModel
        viewModel.iVerifyOtpViewListener = this
        otpFragmentBinding.lifecycleOwner = this
        getBundleData()
//        startTimer(1000)
        customProgressDialog = CustomProgressDialog.getInstance()
        customProgressDialog.dismiss()
        return otpFragmentBinding.root
    }

    fun getBundleData(){
        val args = arguments
        if (args != null){
            if (args.getSerializable(BaseConstant.REGISTER_DTLS_OBJ) != null) {
                objUserData =
                    args.getSerializable(BaseConstant.REGISTER_DTLS_OBJ) as ObjUserData
                initOtpTimer()
            }
        }

    }

    override fun initOtpTimer(){
        startTimer(1000)
    }



    fun startTimer(timerDelay: Int) {
        try {
            if (ratetimer == null) {
                ratetimer = Timer()
                initializeTimerTask()
                ratetimer?.schedule(
                    updateSyncTimer,
                    timerDelay.toLong(),
                    1000.toLong()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initializeTimerTask() {
        try {
            otpFragmentBinding.txtTimer.visibility = View.VISIBLE
            updateSyncTimer = object : TimerTask() {
                override fun run() {
                    handler.post(Runnable {
                        try {
                            if (ratetimer != null) {
                                if (otpCounter == 1) {
                                    stopRateTimer()
                                    otpCounter = 30
                                    isResendGenerateCodeRequest(
                                        requireActivity(),
                                        true,
                                        otpCounter
                                    )
                                } else {
                                    otpCounter--
                                    isResendGenerateCodeRequest(
                                        requireActivity(),
                                        false,
                                        otpCounter
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopRateTimer() {
        try {
            if (ratetimer != null) {
                ratetimer?.cancel()
                ratetimer = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isResendGenerateCodeRequest(
        activity: Activity,
        isResend: Boolean,
        timSec: Int
    ) {
        try {
            if (isResend) {
                otpFragmentBinding.txtTimer.visibility = View.GONE

            }
            else {
                viewModel.timer.set(resources.getString(R.string.timer_text,timSec.toString()))

            }
        }catch(e: Exception) {
                e.printStackTrace()

        }
    }

    override fun verifyOtp() {
        objVerifyOtpReq = objVerifyOtpReq.copy(userId = objUserData.userID,otp=viewModel.otp.get())
        viewModel.callVerifyOtpApi(objVerifyOtpReq)
    }

    override fun launchDashboard(objVerifyDtls: ObjVerifyDtls) {
        startActivity(DashboardActivity.newIntent(getActivity(),objVerifyDtls))
    }

    override fun onOtpVerifyFailure(objVerifyOtpResponseMain: ObjVerifyOtpResponseMain) {
       Toast.makeText(requireContext(),objVerifyOtpResponseMain.objVerifyResponse.objResponseStatusHdr.statusDescr,Toast.LENGTH_LONG).show()
    }

    override fun onBackClick() {
        findNavController().navigate(R.id.action_OTPFragment_To_LoginFragment)
    }
    override fun resendOtpReq(){
        viewModel.callLoginApi(objUserData.mobile!!)
    }
    override fun onResenOtpFailure(objLoginResponseMain: ObjLoginResponseMain) {
        Toast.makeText(requireContext(),objLoginResponseMain.objLoginResponse.objResponseStatusHdr.statusDescr,Toast.LENGTH_LONG).show()

    }
}