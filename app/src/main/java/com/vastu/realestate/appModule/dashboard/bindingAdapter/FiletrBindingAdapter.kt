package com.vastu.realestate.appModule.dashboard.bindingAdapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.RangeSlider
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.FilterViewModel
import com.vastu.realestate.appModule.utils.BaseUtils
import java.util.*

object FiletrBindingAdapter {
    @BindingAdapter("viewmodel")
    @JvmStatic
    fun RangeSlider.setLimits(filterViewModel: FilterViewModel){
        addOnSliderTouchListener(object :RangeSlider.OnSliderTouchListener{

            override fun onStartTrackingTouch(slider: RangeSlider) {
                setValueToLimits(filterViewModel,id,values)

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {

                setValueToLimits(filterViewModel,id,values)
                addChip(filterViewModel,id)
            }

        })
        addOnChangeListener(object :RangeSlider.OnChangeListener{
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                setValueToLimits(filterViewModel,id,values)
            }

        })
    }
    fun setValueToLimits(filterViewModel:FilterViewModel,id:Int,values:List<Float>){
        when(id){
            R.id.budgetRangeSlider->{
                filterViewModel.lowerLimit.set(BaseUtils.amountFormatter(values[0].toInt()))
                filterViewModel.upperLimit.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
            R.id.rangeSliderForSqFt->{
                filterViewModel.lowerLimitForPerSq.set(BaseUtils.amountFormatter(values[0].toInt()))
                filterViewModel.upperLimitForPerSq.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
            R.id.rangeSliderForBuildupAr->{
                filterViewModel.lowerLimitForBuildupArea.set(BaseUtils.amountFormatter(values[0].toInt()))
                filterViewModel.upperLimitForBuildupArea.set(BaseUtils.amountFormatter(values[1].toInt()))
            }
        }
    }

    fun addChip(filterViewModel: FilterViewModel,id: Int){
        when(id){
            R.id.budgetRangeSlider->{
                filterViewModel.addChip("Budget:"+filterViewModel.lowerLimit.get() +"-"+filterViewModel.upperLimit.get())

            }
            R.id.rangeSliderForSqFt->{
                filterViewModel.addChip("Price Per Sq.Ft:"+filterViewModel.lowerLimitForPerSq.get()+"-"+filterViewModel.upperLimitForPerSq.get())

            }
            R.id.rangeSliderForBuildupAr->{
                filterViewModel.addChip("Super Builtup area(ft):"+filterViewModel.lowerLimitForBuildupArea.get()+"-"+filterViewModel.upperLimitForBuildupArea.get())

            }
        }
    }
    @BindingAdapter("isChecked")
    @JvmStatic
    fun AppCompatCheckBox.isSelected(filterViewModel: FilterViewModel){
        setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                when(id){
                    R.id.CheckHouses->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckApartment->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }

                    R.id.CheckBuilderFloor->{
                    if(isChecked){
                        filterViewModel.addChip(text as String)
                    }
                    else
                        filterViewModel.removeChip(id)
                }
                    R.id.CheckFarmHouse->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckFurnished->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckUnfurnished ->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckSemiFurnished ->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckUnderConst->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckReadyToMove ->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckNewLaunch ->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckOwner ->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckDealer->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }
                    R.id.CheckBuilder->{
                        if(isChecked){
                            filterViewModel.addChip(text as String)
                        }
                        else
                            filterViewModel.removeChip(id)
                    }

                }
            }

        })



    }
    @BindingAdapter("viewmodel")
    @JvmStatic
    fun TextView.changeBackground(filterViewModel:FilterViewModel){
        setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (Objects.equals(getBackground().getConstantState(), ContextCompat.getDrawable(context,R.drawable.filter_option_background_on_disselect)!!.getConstantState())) {
                    setBackgroundResource(R.drawable.filter_option_background_onselect)
                    filterViewModel.addChip(text as String)
                }
                else{
                    setBackgroundResource(R.drawable.filter_option_background_on_disselect)
                    filterViewModel.removeChip(id)
                }


                }

        })
    }
    @BindingAdapter("setVisibility")
    @JvmStatic
    fun LinearLayout.setVisibility(view: Boolean){
        when(id){
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