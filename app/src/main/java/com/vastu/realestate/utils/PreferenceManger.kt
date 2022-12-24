package com.vastu.realestate.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vastu.slidercore.model.response.GetPropertySliderImagesResponse

object PreferenceManger {
    lateinit var preferences: SharedPreferences
    private const val PREFERENCE_FILE_NAME ="_Vastu"
    private val gson = Gson()

    fun with(application: Application){
        preferences = application.getSharedPreferences(PREFERENCE_FILE_NAME,Context.MODE_PRIVATE)
    }

    fun <T> put(user:T,key:String){
        val jsonString = GsonBuilder().create().toJson(user)
        preferences.edit().putString(key,jsonString).apply()
    }
    inline fun <reified T> get(key:String):T?{
        val value = preferences.getString(key,null)
        return GsonBuilder().create().fromJson(value,T::class.java)
    }
    fun clearPreferences(){
       preferences.edit().remove(PREFERENCE_FILE_NAME).clear().apply()
    }

    fun saveSlider(sliderList: GetPropertySliderImagesResponse, key:String) {
          preferences.edit()
            .putString(key, gson.toJson(sliderList))
            .apply()
    }

    fun getSlider(key:String):GetPropertySliderImagesResponse {
        val sliderList = preferences.getString(key, null)
        val type = object : TypeToken<GetPropertySliderImagesResponse>() {}.type
        return gson.fromJson(sliderList, type)
    }
}