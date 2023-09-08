package com.vastu.realestate.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.R
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object BaseConstant {
  var EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
  var NAME_REGEX ="^(?=.*[a-z\\u0900-\\u097FA-Z0-9]).+\$"
  var MOBILE_REGEX ="^[6-9]\\d{9}\$"
  var ADDRESS_REGEX ="^[#.0-9a-zA-Z\\s,-]+\$"

  var REGISTER_DTLS_OBJ = "verifyotp"
  var PROPERTY_DETAILS = "PROPERTY_DETAILS"
  var PROPERTY_ID = "PROPERTY_ID"
  var AMOUNTFORMAT = "#,###,###"
  var FILTERTYPELIST = "FilterTypeList.json"
  var ENQUIRY_RESPONSE = "ENQUIRY_RESPONSE"
  var LOAN_DATA = "LOAN_DATA"
  var ASSIGNED_LOAN_DATA="ASSIGNED_LOAN_DATA"
  var ASSIGNED_PROPERTY_DATA="ASSIGNED_PROPERTY_DATA"
  var STATUS = "STATUS"
  var IS_FROM_PROPERTY_LIST = "IS_FROM_PROPERTY_LIST"
  var IS_FROM_PROPERTIES = "IS_FROM_PROPERTIES"

  const val PICK_FROM_GALLERY = 100
  const val PDF_SELECTION = 101

  const val ADMIN ="1"
  const val CUSTOMER ="2"
  const val EMPLOYEES ="3"
  const val BUILDER ="4"

  const val ADD_PROPERTY_STATUS="1"
  const val EMPLOYEE_ID ="EMPLOYEE ID"
  const val PROPERTY_DATA = "PROPERTY_DATA"

  const val TO_DO = "1"
  const val INPROGRSS ="2"
  const val COMPLETE = "3"
  const val USER_TYPE= "USER TYPE"
  const val LANGUAGE_FLOW="drawer_language"
}