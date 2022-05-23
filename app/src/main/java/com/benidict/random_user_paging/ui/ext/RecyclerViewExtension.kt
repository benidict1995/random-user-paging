package com.benidict.random_user_paging.ui.ext

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.benidict.random_user_paging.R

fun Context.createItemSeparator(): DividerItemDecoration {
    val itemSeparator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(this, R.drawable.view_separator)?.let {
        itemSeparator.setDrawable(it)
    }
    return itemSeparator
}