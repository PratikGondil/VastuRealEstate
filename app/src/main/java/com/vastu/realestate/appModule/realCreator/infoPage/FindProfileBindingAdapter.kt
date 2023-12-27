package com.vastu.realestate.appModule.realCreator.infoPage


import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.vastu.realCreator.realCreatorSearch.model.ProfileDaum
import com.vastu.realestate.R
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData

object FindProfileBindingAdapter {
    var isValidCity:Boolean=false
    var isValidSubArea:Boolean=false
    var isProfile:Boolean=false
    var isTaluka:Boolean=false


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
                    R.id.autoCompleteCity ->{
                        isValidCity = true
                        viewModel.city.value = adapter.getItem(i) as ObjTalukaDataList?
                        parentLayout.helperText = ""
                    }
                    R.id.autoCompleteAreaList ->{
                       isValidSubArea = true
                        viewModel.subArea.set(adapter.getItem(i) as ObjCityAreaData?)
                        parentLayout.helperText = ""
                    }
                    R.id.autoProfileList->{
                        isProfile = true
                        viewModel.profile.value=adapter.getItem(i) as ProfileDaum
                        parentLayout.helperText = ""
                    }
                    R.id.autoCompleteTaluka->{
                        isTaluka = true
                        viewModel.taluka.value=adapter.getItem(i) as ObjTalukaDataList?
                        parentLayout.helperText = ""
                        viewModel.isTalukaSelected()
                    }

                }
                changeSubmitBtnState(viewModel, context)
            }
    }
    fun changeSubmitBtnState(findProfileViewModel: FindProfileViewModel,context: Context) {
        if (isValidCity && isValidSubArea && isTaluka && isProfile) {
            findProfileViewModel.isBtnEnable.set(true)
            findProfileViewModel.btnBackground.set(
                ContextCompat.getDrawable(context,
                    R.drawable.round_button_background))
        } else {
            findProfileViewModel.isBtnEnable.set(false)
            findProfileViewModel.btnBackground.set(
                ContextCompat.getDrawable(context,
                    R.drawable.button_inactive_background))

        }
    }
}