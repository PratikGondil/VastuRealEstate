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
  var  EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
  var NAME_REGEX ="^(?=.*[a-zA-Z0-9]).+\$"
  var MOBILE_REGEX ="^[6-9]\\d{9}\$"

  var REGISTER_DTLS_OBJ = "verifyotp"
  var PROPERTY_DETAILS = "PROPERTY_DETAILS"
  var AMOUNTFORMAT = "#,###,###"
  var FILTERTYPELIST = "FilterTypeList.json"
  var ENQUIRY_RESPONSE = "ENQUIRY_RESPONSE"
  var STATUS = "STATUS"
}