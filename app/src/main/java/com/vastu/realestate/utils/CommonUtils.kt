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

class CommonUtils {


companion object {
    fun showImageFromURL(
        context: Context?,
        urlPath: String?,
        imgFile: ImageView,
        placeholder:Int
    ) {
        try {
            if (context != null && urlPath != null && !TextUtils.isEmpty(urlPath)) {
                imgFile.colorFilter = null
                Glide.get(context).clearMemory()
                imgFile.clearColorFilter()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isValidContextForGlide(context)) {
                        Glide.with(context)
                            .load(urlPath)
                            .placeholder(placeholder)
                            .apply(
                                RequestOptions()
                                    .override(
                                        Target.SIZE_ORIGINAL,
                                        Target.SIZE_ORIGINAL
                                    )
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .timeout(6000)
                            ) // 60 second timeout
                            .error(R.color.switch_thumb_normal_material_light) //R.drawable.user
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    imgFile.setImageResource(R.color.switch_thumb_normal_material_light)
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    imgFile.tag = "server_file"
                                    return false
                                }
                            })
                            .into(imgFile)
                    }
                }, 500)
            } else {
                imgFile.setImageResource(R.color.switch_thumb_normal_material_light)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun isValidContextForGlide(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            val activity = context
            if (activity.isDestroyed || activity.isFinishing) {
                return false
            }
        }
        return true
    }
    }
}

