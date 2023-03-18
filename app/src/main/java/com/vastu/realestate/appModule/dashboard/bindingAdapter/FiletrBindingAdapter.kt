package com.vastu.realestate.appModule.dashboard.bindingAdapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.RangeSlider
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import java.util.*

object FiletrBindingAdapter {

    @BindingAdapter("lowerLimit","upperLimit","updatedValue")
    @JvmStatic
    fun RangeSlider.setValuesToSlider(lowerLimit:String,upperLimit:String,realEstateViewModel: RealEstateViewModel){
        var cleanLowerLimit = lowerLimit.replace(",", "")
        var cleanUpperLimit = upperLimit.replace(",", "")
        if(id.equals(R.id.budgetRangeSlider)){

            setValues(cleanLowerLimit.toFloat(),cleanUpperLimit.toFloat())
            if(!(cleanLowerLimit.equals(context.getString(R.string.budget_lower_limit)))||!(cleanUpperLimit.equals(context.getString(R.string.budget_upper_limit)))){
                realEstateViewModel.removeChip(id)
                addChip(context,realEstateViewModel,id)
                Log.d("Chip add ","Chip added in setvalues fun")
            }

        }

        else if(id.equals(R.id.rangeSliderForBuildupAr)){
            setValues(cleanLowerLimit.toFloat(),cleanUpperLimit.toFloat())
            if(!(cleanLowerLimit.equals(context.getString(R.string.budget_lower_limit)))||!(cleanUpperLimit.equals(context.getString(R.string.buildup_area_upper_limit))))
            {
                realEstateViewModel.removeChip(id)
                addChip(context,realEstateViewModel,id)}
        }
    }
    @BindingAdapter("viewmodel")
    @JvmStatic
    fun RangeSlider.setLimits(realEstateViewModel: RealEstateViewModel){
        addOnSliderTouchListener(object :RangeSlider.OnSliderTouchListener{

            override fun onStartTrackingTouch(slider: RangeSlider) {
                if(id.equals(R.id.budgetRangeSlider)){
                setValueToLimits(realEstateViewModel,id,values)
                }

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                if(id.equals(R.id.budgetRangeSlider)) {
                    setValueToLimits(realEstateViewModel, id, values)
                    realEstateViewModel.removeChip(id)
                    addChip(context, realEstateViewModel, id)
                    Log.d("Chip add ","Chip added in setLimits fun")
                }
            }

        })
        addOnChangeListener(object :RangeSlider.OnChangeListener{
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                if(fromUser)
                setValueToLimits(realEstateViewModel,id,values)
            }

        })
    }
    fun setValueToLimits(realEstateViewModel:RealEstateViewModel,id:Int,values:List<Float>){
        when(id){
            R.id.budgetRangeSlider->{
                realEstateViewModel.lowerLimit.set(BaseUtils.amountFormatter(values[0].toInt()))
                realEstateViewModel.upperLimit.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
            R.id.rangeSliderForSqFt->{
                realEstateViewModel.lowerLimitForPerSq.set(BaseUtils.amountFormatter(values[0].toInt()))
                realEstateViewModel.upperLimitForPerSq.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
            R.id.rangeSliderForBuildupAr->{
                realEstateViewModel.lowerLimitForBuildupArea.set(BaseUtils.amountFormatter(values[0].toInt()))
                realEstateViewModel.upperLimitForBuildupArea.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
        }
    }

    fun addChip(context: Context,realEstateViewModel: RealEstateViewModel,id: Int){
        when(id){
            R.id.budgetRangeSlider->{
                realEstateViewModel.addChip(context.getString(R.string.chip_text_budget)+realEstateViewModel.lowerLimit.get() +"-"+realEstateViewModel.upperLimit.get())

            }
            R.id.rangeSliderForSqFt->{
                realEstateViewModel.addChip(context.getString(R.string.chip_text_price_per_sq)+realEstateViewModel.lowerLimitForPerSq.get()+"-"+realEstateViewModel.upperLimitForPerSq.get())

            }
            R.id.rangeSliderForBuildupAr->{
                realEstateViewModel.addChip(context.getString(R.string.chip_text_builtup_area)+realEstateViewModel.lowerLimitForBuildupArea.get()+"-"+realEstateViewModel.upperLimitForBuildupArea.get())

            }
        }
    }
    @BindingAdapter("isChecked")
    @JvmStatic
    fun AppCompatCheckBox.isSelected(realEstateViewModel: RealEstateViewModel){
        setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                when(id){
                    R.id.checkHouses->{
                        if(isChecked){
                            realEstateViewModel.isHousesSelected.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else{
                            realEstateViewModel.isHousesSelected.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkApartment->{
                        if(isChecked){
                            realEstateViewModel.isApartmentSelected.set(true)

                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isApartmentSelected.set(false)

                            realEstateViewModel.removeChip(id)
                        }
                    }

                    R.id.checkBuilderFloor->{
                    if(isChecked){
                        realEstateViewModel.isBuilerFloorSelected.set(true)
                        realEstateViewModel.addChip(text as String)
                    }
                    else{
                        realEstateViewModel.isBuilerFloorSelected.set(false)
                        realEstateViewModel.removeChip(id)
                    }

                }
                    R.id.check_farm_house->{
                        if(isChecked){
                            realEstateViewModel.isFarmHousesSelected.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isFarmHousesSelected.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkFurnished->{
                        if(isChecked){
                            realEstateViewModel.isFurnished.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isFurnished.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkUnfurnished ->{
                        if(isChecked){
                            realEstateViewModel.isUnfurnished.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isUnfurnished.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkSemiFurnished ->{
                        if(isChecked){
                            realEstateViewModel.isSemiFurnished.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isSemiFurnished.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkUnderConst->{
                        if(isChecked){
                            realEstateViewModel.isUnderConst.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isUnderConst.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkReadyToMove ->{
                        if(isChecked){
                            realEstateViewModel.isReadyToMove.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isReadyToMove.set(false)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.checkNewLaunch ->{
                        if(isChecked){
                            realEstateViewModel.isNewLaunch.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isNewLaunch.set(true)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.check_owner ->{
                        if(isChecked){
                            realEstateViewModel.isOwner.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isOwner.set(true)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.check_dealer->{
                        if(isChecked){
                            realEstateViewModel.isDealer.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isDealer.set(true)
                            realEstateViewModel.removeChip(id)
                        }
                    }
                    R.id.check_builder->{
                        if(isChecked){
                            realEstateViewModel.isBuilder.set(true)
                            realEstateViewModel.addChip(text as String)
                        }
                        else {
                            realEstateViewModel.isBuilder.set(true)
                            realEstateViewModel.removeChip(id)
                        }
                    }


                }
            }

        })



    }
    @BindingAdapter("viewmodel")
    @JvmStatic
    fun TextView.changeBackground(realEstateViewModel:RealEstateViewModel){
        setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (Objects.equals(getBackground().getConstantState(), ContextCompat.getDrawable(context,R.drawable.filter_option_background_on_disselect)!!.getConstantState())) {
                    setBackgroundResource(R.drawable.filter_option_background_onselect)
                    realEstateViewModel.addChip(text as String)
                }
                else{
                    setBackgroundResource(R.drawable.filter_option_background_on_disselect)
                    realEstateViewModel.removeChip(id)
                }


                }

        })
    }
    @BindingAdapter("setVisibility")
    @JvmStatic
    fun LinearLayout.setVisibility(view: Boolean){
        when(id){
            R.id.llSubAreaLayout->
                visibility = manageVisibility(view)
            R.id.llBudgetLayout->
                visibility = manageVisibility(view)
            R.id.llProprtyType->
                visibility = manageVisibility(view)
            R.id.llPricePerSqFtLayout->
                visibility = manageVisibility(view)
            R.id.llNoOfBedroom->
                visibility = manageVisibility(view)
            R.id.llNoOfBathroom->
                visibility = manageVisibility(view)
            R.id.llFurnishingType->
                visibility = manageVisibility(view)
            R.id.llConstructionStatus->
                visibility = manageVisibility(view)
            R.id.llListedBy->
                visibility = manageVisibility(view)
            R.id.llBuildupAreaLayout->
                visibility = manageVisibility(view)
            R.id.llChangeSort->
                visibility = manageVisibility(view)
        }

    }
    fun manageVisibility(visibility: Boolean):Int{
        if(visibility){
             return View.VISIBLE
        }
        else
            return View.GONE
    }
}