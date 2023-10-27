package com.vastu.realestate.appModule.ourServies.planForAdvertisement

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField

object PlanForAdvertisementBindingAdapter {
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
}