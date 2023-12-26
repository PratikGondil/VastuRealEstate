package com.vastu.realestate.appModule.realCreator.infoPage

import android.R
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.google.android.material.textfield.TextInputLayout
import com.vastu.realestate.appModule.signUp.bindingAdapter.SignUpBindingAdapter
import com.vastu.realestate.appModule.signUp.viewModel.SignUpViewModel
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData

object FindProfileBindingAdapter {
    var isValidCity:Boolean=false
    var isValidSubArea:Boolean=false

        @JvmStatic
        @BindingAdapter(
            "allItems",
            "selectedItem",
            requireAll = false
        )
        fun <T> Spinner.setCountries(
            allItems: ObservableField<List<T>>,
            selectedItem: ObservableField<T>,
        ) {
            adapter = ArrayAdapter(
                context,
                R.layout.simple_spinner_dropdown_item,
                allItems.get() ?: listOf()
            )

            val selection = allItems.get()?.indexOf(selectedItem.get())
            selection?.let { setSelection(it) }

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedItem.set(allItems.get()?.getOrNull(position))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }

        }


    @BindingAdapter("android:onItemClick", "android:context","tilLayout")
    @JvmStatic
    fun AutoCompleteTextView.autoCompleteTextClick(
        viewModel: FindProfileViewModel, context: Context, parentLayout: TextInputLayout
    ) {

        onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
                when (id) {
                    com.vastu.realestate.R.id.autoCompleteCity ->{
                        SignUpBindingAdapter.isValidCity = true
                        viewModel.city.value = adapter.getItem(i) as ObjTalukaDataList?
                        parentLayout.helperText = ""
                    }
                    com.vastu.realestate.R.id.autoCompleteAreaList ->{
                        SignUpBindingAdapter.isValidSubArea = true
                        viewModel.subArea.set(adapter.getItem(i) as ObjCityAreaData?)
                        parentLayout.helperText = ""
                    }

                }
                changeSubmitBtnState(viewModel, context)
            }
    }
    fun changeSubmitBtnState(findProfileViewModel: FindProfileViewModel,context: Context) {
        if (isValidCity && isValidSubArea) {
            findProfileViewModel.isBtnEnable.set(true)
            findProfileViewModel.btnBackground.set(
                ContextCompat.getDrawable(context,
                    com.vastu.realestate.R.drawable.round_button_background))
        } else {
            findProfileViewModel.isBtnEnable.set(false)
            findProfileViewModel.btnBackground.set(
                ContextCompat.getDrawable(context,
                    com.vastu.realestate.R.drawable.button_inactive_background))

        }
    }
}