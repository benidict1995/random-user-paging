package com.benidict.random_user_paging.ui.ext

import android.widget.ImageView
import com.benidict.random_user_paging.R
import com.bumptech.glide.Glide

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}