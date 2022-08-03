package com.example.customview.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
import com.google.android.material.shape.ShapeAppearanceModel


fun Context.makeMaterialShapeDrawable(): MaterialShapeDrawable {

    val topEdgeTreatment: EdgeTreatment =
        BottomAppBarTopEdgeTreatment(dpToPx(32f),dpToPx(24f), dpToPx(22f), dpToPx(8f))
    val shapeAppearanceModel = ShapeAppearanceModel.builder().setTopEdge(topEdgeTreatment).build()

    return MaterialShapeDrawable().apply {
        setShapeAppearanceModel(shapeAppearanceModel)
        shadowCompatibilityMode = SHADOW_COMPAT_MODE_ALWAYS
        paintStyle = Paint.Style.FILL
//        initializeElevationOverlay(this@makeMaterialShapeDrawable)
    }.also {
        DrawableCompat.setTintList(it, ColorStateList.valueOf(Color.BLACK))
    }


}