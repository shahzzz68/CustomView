package com.example.customview.utils

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}


fun Context.pxToDp(px: Float): Int {
    return (px.toInt() / resources.displayMetrics.density).toInt()
}