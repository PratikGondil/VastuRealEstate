package com.vastu.realestate.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object BaseConstant {
  var  EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
  var NAME_REGEX ="^(?=.*[a-zA-Z0-9]).+\$"
  var MOBILE_REGEX ="^[6-9]\\d{9}\$"

  var REGISTER_DTLS_OBJ = "verifyotp"
  var PROPERTY_DETAILS = "PROPERTY_DETAILS"
  var ENQUIRY_RESPONSE = "ENQUIRY_RESPONSE"
  var STATUS = "STATUS"

  fun hasConnectivity(context: Context): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val nw = connectivityManager.activeNetwork ?: return false
      val actNw = connectivityManager.getNetworkCapabilities(nw)
      actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
      ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
        NetworkCapabilities.TRANSPORT_BLUETOOTH
      ))
    } else {
      val nwInfo = connectivityManager.activeNetworkInfo
      nwInfo != null && nwInfo.isConnected
    }
  }
}