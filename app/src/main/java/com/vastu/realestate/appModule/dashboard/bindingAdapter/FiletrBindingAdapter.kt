package com.vastu.realestate.appModule.dashboard.bindingAdapter

import android.content.Context
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
    @BindingAdapter("viewmodel")
    @JvmStatic
    fun RangeSlider.setLimits(realEstateViewModel: RealEstateViewModel){
        addOnSliderTouchListener(object :RangeSlider.OnSliderTouchListener{

            override fun onStartTrackingTouch(slider: RangeSlider) {
                setValueToLimits(realEstateViewModel,id,values)

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {

                setValueToLimits(realEstateViewModel,id,values)
                realEstateViewModel.removeChip(id)
                addChip(context,realEstateViewModel,id)
            }

        })
        addOnChangeListener(object :RangeSlider.OnChangeListener{
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
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
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkApartment->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }

                    R.id.checkBuilderFloor->{
                    if(isChecked){
                        realEstateViewModel.addChip(text as String)
                    }
                    else
                        realEstateViewModel.removeChip(id)
                }
                    R.id.check_farm_house->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkFurnished->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkUnfurnished ->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkSemiFurnished ->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkUnderConst->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkReadyToMove ->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.checkNewLaunch ->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.check_owner ->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.check_dealer->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
                    }
                    R.id.check_builder->{
                        if(isChecked){
                            realEstateViewModel.addChip(text as String)
                        }
                        else
                            realEstateViewModel.removeChip(id)
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