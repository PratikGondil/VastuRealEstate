package com.vastu.realestate.appModule.dashboard.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.vastu.loanenquirycore.model.response.interest.property.InterestedInData
import com.vastu.loanenquirycore.model.response.occupation.OccupationData
import com.vastu.loanenquirycore.model.response.ownership.OwnershipData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.AddPropertyViewModel
import com.vastu.realestate.appModule.enquiry.bindingadapter.AddPropertyBindingAdapter
import com.vastu.realestate.appModule.enquiry.viewModel.AddPropertyEnquiryViewModel
import com.vastu.realestate.appModule.signUp.bindingAdapter.SignUpBindingAdapter
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.utils.BaseConstant

object AddPropertyBindingAdapter {
    var isValidPropertyTitle:Boolean=false
    var isValidPropertyType:Boolean=false
    var isValidSellType:Boolean=false
    var isValidState:Boolean=false
    var isValidCity:Boolean=false
    var isValidSubArea:Boolean=false
    var isValidBuildYear:Boolean=false
    var isValidArea:Boolean=false
    var isValidPropertyAddress:Boolean=false
    var isValidPrice:Boolean=false
    var isValidBookingAmount = false
    var isValidBedroom = false
    var isValidBathroom = false
    var isValidKitchen = false
    var isValidAvailability = false
    var isValidDescription = false
    var isValidHighlights = false

    @BindingAdapter("valid")
    @JvmStatic
    fun TextInputEditText.validateAddPropertyData(viewModel: AddPropertyViewModel) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (id) {
                    R.id.edtPropertyTitle ->{
                        if(isValidName(input.toString().trim())) {
                            isValidPropertyTitle = true
                            viewModel.propertyTitle.set(input!!.toString().trim())
                            viewModel.propertyTitleValid.set("")
                        }
                        else {
                            AddPropertyBindingAdapter.isValidFirstName = false
                            viewModel.propertyTitleValid.set(context.getString(R.string.property_title_error))
                        }
                    }
                    R.id.edtStates ->{
                        if(isValidName(input.toString())){
                           isValidState= true
                            viewModel.state.set(input!!.toString().trim())
                            viewModel.stateValid.set("")
                        }
                        else {
                            isValidState = false
                            viewModel.stateValid.set(context.getString(R.string.state_error))
                        }
                    }
                    R.id.edtPrice ->{
                        if(isValidAmount(input.toString())) {
                            isValidPrice = true
                            viewModel.price.set(input!!.toString().trim())
                            viewModel.priceValid.set("")
                        }
                        else {
                          isValidPrice = false
                          viewModel.priceValid.set(context.getString(R.string.property_price_error))
                        }
                    }
                    R.id.edtBookingAmount ->{
                        if(isValidAmount(input.toString())) {
                            isValidBookingAmount = true
                            viewModel.bookingAmount.set(input!!.toString().trim())
                            viewModel.bookingAmountValid.set("")
                        }
                        else {
                            isValidBookingAmount = false
                            viewModel.bookingAmountValid.set(context.getString(R.string.property_amount_error))
                        }
                        setSelection(text!!.length)
                    }

                    R.id.edtPropertyAddress ->{
                        if(isValidAddress(input.toString())){
                            isValidPropertyAddress = true
                            viewModel.propertyAddress.set(input!!.toString())
                            viewModel.propertyAddressValid.set("")
                        }
                        else {
                            isValidPropertyAddress = false
                            viewModel.propertyAddressValid.set(context.getString(R.string.address_error))
                        }
                    }
                    R.id.edtArea ->{
                        if(isValidArea(input.toString())){
                            isValidArea = true
                            viewModel.areaSqFt.set(input!!.toString().trim())
                            viewModel.areaSqFtValid.set("")
                        }
                        else {
                            isValidArea = false
                            viewModel.areaSqFtValid.set(context.getString(R.string.area_error))
                        }
                    }

                    R.id.edtBedroom ->{
                        if(isValidRooms(input.toString())){
                            isValidBedroom = true
                            viewModel.bedroom.set(input!!.toString().trim())
                            viewModel.bedroomValid.set("")
                        }
                        else{
                            isValidBedroom = false
                            viewModel.bedroomValid.set(context.getString(R.string.required))
                        }
                    }

                    R.id.edtBathroom ->{
                        if(isValidRooms(input.toString())){
                            isValidBathroom = true
                            viewModel.bathroom.set(input!!.toString().trim())
                            viewModel.bathroomValid.set("")
                        }
                        else{
                            isValidBathroom = false
                            viewModel.bathroomValid.set(context.getString(R.string.required))
                        }
                    }

                    R.id.edtGarage ->  viewModel.garage.set(input!!.toString().trim())

                    R.id.edtBalcony -> viewModel.balcony.set(input!!.toString().trim())

                    R.id.edtSwimmingPool -> viewModel.swimmingPool.set(input!!.toString().trim())

                    R.id.edtFloors -> viewModel.floors.set(input!!.toString().trim())

                    R.id.edtKitchen ->{
                        if(isValidRooms(input.toString())){
                            isValidKitchen = true
                            viewModel.kitchen.set(input!!.toString().trim())
                            viewModel.kitchenValid.set("")
                        }
                        else{
                            isValidKitchen = false
                            viewModel.kitchenValid.set(context.getString(R.string.required))
                        }
                    }
                    R.id.edtDescription ->{
                        if(isValidAddress(input.toString())){
                            isValidDescription = true
                            viewModel.description.set(input!!.toString().trim())
                            viewModel.descriptionValid.set("")
                        }
                        else {
                            isValidDescription = false
                            viewModel.descriptionValid.set(context.getString(R.string.description_error))
                        }
                    }
                    R.id.edtHighlights ->{
                        if(isValidAddress(input.toString())){
                            isValidHighlights = true
                            viewModel.highlights.set(input!!.toString().trim())
                            viewModel.highlightsValid.set("")
                        }
                        else {
                            isValidHighlights = false
                            viewModel.highlightsValid.set(context.getString(R.string.highlights_error))
                        }
                    }
                }
                changeSubmitBtnState(viewModel, context)
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    fun isValidRooms(room:String):Boolean{
        return room.length == 1
    }
    fun isValidArea(area:String):Boolean{
        return area.length in 3..10
    }
    fun isValidAmount(budget:String):Boolean{
        return budget.length in 2..15
    }
    fun isValidAddress(address:String):Boolean{
        return BaseConstant.ADDRESS_REGEX.toRegex().containsMatchIn(address)
    }
    fun isValidPropertyAddress(address:String):Boolean{
        return address.length > 6
    }
    fun isValidName(name:String):Boolean{
        return name.length > 6
    }

    @BindingAdapter("android:onItemClick", "android:context")
    @JvmStatic
    fun AutoCompleteTextView.autoCompleteTextClick(
        viewModel: AddPropertyViewModel, context: Context
    ) {
        onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val imm =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
                when (id) {
                    R.id.autoCompletePropertyType ->{
                            isValidPropertyType = true
                            viewModel.propertyType.set(adapter.getItem(i) as String)
                            viewModel.propertyTypeValid.set("")
                    }
                    R.id.autoCompleteSellType ->{
                        isValidSellType = true
                        viewModel.sellType.set(adapter.getItem(i) as String)
                        viewModel.sellTypeValid.set("")
                    }
                    R.id.autoCompleteBuildYear->{
                        isValidBuildYear = true
                        viewModel.buildYear.set(adapter.getItem(i) as String)
                        viewModel.buildYearValid.set("")
                    }
                    R.id.autoCompleteAvailability->{
                       isValidAvailability = true
                        viewModel.availability.set(adapter.getItem(i) as String)
                        viewModel.availabilityValid.set("")
                    }
                    R.id.autoCompleteCity ->{
                        isValidCity = true
                        viewModel.city.value = adapter.getItem(i) as ObjTalukaDataList?
                        viewModel.cityValid.set("")
                    }
                    R.id.autoCompleteSubAreaList ->{
                        isValidSubArea = true
                        viewModel.subArea.value = adapter.getItem(i) as ObjCityAreaData?
                        viewModel.subAreaValid.set("")
                    }
                }
               changeSubmitBtnState(viewModel, context)
            }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("context","isBtnEnable","btnBackground")
    @JvmStatic
    fun AppCompatButton.isButtonEnable(context: Context, isBtnEnable: Boolean, btnBackground: Drawable){
        if(isBtnEnable){
            isEnabled =true
            background =btnBackground
        }
        else{
            isEnabled =false
            background =btnBackground

        }
    }

    fun changeSubmitBtnState(viewModel: AddPropertyViewModel, context: Context) {
        if (isValidPropertyTitle && isValidPropertyType && isValidSellType && isValidState && isValidCity && isValidSubArea && isValidPropertyAddress
            && isValidArea && isValidBookingAmount && isValidPrice && isValidBuildYear && isValidBedroom && isValidBathroom && isValidKitchen &&
                isValidDescription && isValidAvailability && isValidHighlights) {
            viewModel.isBtnEnable.set(true)
            viewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.round_button_background))
        } else {
            viewModel.isBtnEnable.set(false)
            viewModel.btnBackground.set(ContextCompat.getDrawable(context, R.drawable.button_inactive_background))
        }
    }
}