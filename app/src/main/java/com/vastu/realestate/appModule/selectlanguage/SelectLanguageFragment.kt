  package com.vastu.realestate.appModule.selectlanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vastu.realestate.R
import com.vastu.realestate.appModule.contactus.ContactUsViewModel
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.FragmentSelectLanguageBinding


  class SelectLanguageFragment : BaseFragment() {

    lateinit var selectLanguageViewModel: SelectLanguageViewModel
    lateinit var selectLanguageBinding: FragmentSelectLanguageBinding
      lateinit var radioButton: RadioButton

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectLanguageViewModel = ViewModelProvider(this)[SelectLanguageViewModel::class.java]
        selectLanguageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_language,container,false)
        selectLanguageBinding.selectLanguageViewModel = selectLanguageViewModel
        initView()
        return selectLanguageBinding.root
    }

      private fun initView() {
          selectLanguageBinding.btnNext.setOnClickListener {


          }

      }


  }