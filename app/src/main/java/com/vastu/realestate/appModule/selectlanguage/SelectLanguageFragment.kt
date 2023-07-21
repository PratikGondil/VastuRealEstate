  package com.vastu.realestate.appModule.selectlanguage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.selectlanguage.uiinterface.iSelectLanguage
import com.vastu.realestate.databinding.FragmentSelectLanguageBinding


  class SelectLanguageFragment : BaseFragment(),iSelectLanguage {

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
        selectLanguageViewModel.iSelectLanguage = this
        initView()
        return selectLanguageBinding.root
    }

      private fun initView() {
          setOnCheckedChangeListener()
          selectLanguageBinding.btnNext.setOnClickListener {


          }

      }

      private fun setOnCheckedChangeListener() {
          selectLanguageBinding.radioLanguage.setOnCheckedChangeListener { group, checkedId ->
              selectLanguageViewModel.btnBackground.set(ContextCompat.getDrawable(requireContext(),R.drawable.round_button_background))

          }
      }

      override fun redirectToLoginSignUpPage(selectedLanguageCode: String) {
          setAppLocale(selectedLanguageCode)
          findNavController().navigate(R.id.action_SelectLanguage_to_SignupLoginHome)

      }


  }