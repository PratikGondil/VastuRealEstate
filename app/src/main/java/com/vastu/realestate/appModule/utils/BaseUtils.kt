package com.vastu.realestate.appModule.utils

import android.content.Context
import com.vastu.realestate.utils.BaseConstant
import org.json.JSONObject
import java.io.IOException
import java.text.NumberFormat
import java.util.*

object BaseUtils {
    fun amountFormatter(amount: Int): String {
        val formatter= NumberFormat.getNumberInstance(Locale("en", "in")).format(amount)
//        val formatter = BaseConstant.AMOUNTFORMAT
        return formatter
    }


    fun getFilterTypeList(context: Context): JSONObject {
        var json: JSONObject? = null
        try {
            val inputStream = context.assets.open(BaseConstant.FILTERTYPELIST)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            json = JSONObject(String(buffer))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return json!!
    }
}