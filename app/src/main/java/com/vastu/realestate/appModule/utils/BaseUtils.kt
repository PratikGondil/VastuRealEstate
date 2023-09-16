package com.vastu.realestate.appModule.utils

import android.content.Context
import android.text.Spanned
import androidx.core.text.parseAsHtml
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusData
import com.vastu.realestate.appModule.utils.BaseUtils.htmlToString
import com.vastu.realestate.utils.BaseConstant
import org.json.JSONObject
import org.jsoup.Jsoup
import org.w3c.dom.Document
import java.io.IOException
import java.text.NumberFormat
import java.util.*
object  BaseUtilForBinding{
    fun htmlToStringFormat(str:String):String=htmlToString(str)
}

object BaseUtils {
    fun amountFormatter(amount: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("en", "in")).format(amount)
//        val formatter = BaseConstant.AMOUNTFORMAT
        return formatter
    }

    fun stringToInt() {


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

   fun String.numberToWord(): String {
        var updatedLable = if (this.toInt() >= 10000000) " Cr"
        else if (this.toInt() >= 100000) " Lac"
        else if (this.toInt() >= 1000) " K" else ""

        return this.twoDigit() + updatedLable
    }

    fun String.twoDigit(): String {
        return this.substring(0,2)
    }

    fun getPreviousStatus(
        currentStatus: String,
        listOfLookupData: ArrayList<ObjEnquiryStatusData>
    ): Int {
        var reason: Int = 0
        for (i in listOfLookupData.indices) {
            if (listOfLookupData[i].statusName.equals(currentStatus, ignoreCase = true)) {
                reason = i
            }
        }
        return reason
    }

//    fun htmlToString(str:String): String {
//        return str.parseAsHtml().toString()
//    }
    fun htmlToString(html: String): String {
        val document: org.jsoup.nodes.Document = Jsoup.parse(html)
        return document.toString()
    }

}
