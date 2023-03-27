package com.example.cheers.util

import android.graphics.drawable.Drawable
import android.view.View
import androidx.constraintlayout.widget.Placeholder
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object BindigAdapter {

    @JvmStatic
    @BindingAdapter("loadImg","placeHolder","error")
    fun loadImage(img:com.makeramen.roundedimageview.RoundedImageView,url:String?,
    placeholder: Drawable?,error:Drawable?){
        if (url!=null){
            Glide.with(img).load(
                url
            ).error(error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(placeholder)
                .apply(RequestOptions().fitCenter())
                .into(img)
        }else{
            img.setImageDrawable(placeholder)
        }

    }
    @JvmStatic
    @BindingAdapter("showView")
    fun showView(view:View,isShow:Boolean){
        view.visibility = if (isShow){
            View.VISIBLE
        }
        else{
            View.GONE
        }
    }
}