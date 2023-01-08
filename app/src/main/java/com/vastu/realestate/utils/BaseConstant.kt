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
  var NAME_REGEX ="^(?=.*[a-zA-Z0-9]).+\$"
  var MOBILE_REGEX ="^[6-9]\\d{9}\$"

  var REGISTER_DTLS_OBJ = "verifyotp"
  var PROPERTY_DETAILS = "PROPERTY_DETAILS"
  var PROPERTY_ID = "PROPERTY_ID"
  var AMOUNTFORMAT = "#,###,###"
  var FILTERTYPELIST = "FilterTypeList.json"
  var ENQUIRY_RESPONSE = "ENQUIRY_RESPONSE"
  var LOAN_DATA = "LOAN_DATA"
  var STATUS = "STATUS"
  var IS_FROM_PROPERTY_LIST = "IS_FROM_PROPERTY_LIST"

  const val PICK_FROM_GALLERY = 100

  const val ADMIN ="1"
  const val CUSTOMER ="2"
  const val BUILDER ="3"
  const val EMPLOYEES ="4"

  const val ADD_PROPERTY_STATUS="1"

}