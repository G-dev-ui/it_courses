package com.itcourses.core.ui.util

import android.graphics.Matrix
import android.widget.ImageView

/**
 * Top-crop для ImageView: заполняем по ширине/высоте как centerCrop,
 * но по вертикали прижимаем изображение к верху.
 */
fun ImageView.applyTopCrop() {
    val d = drawable ?: return
    val viewWidth = width - paddingLeft - paddingRight
    val viewHeight = height - paddingTop - paddingBottom
    if (viewWidth <= 0 || viewHeight <= 0) return

    val drawableWidth = d.intrinsicWidth
    val drawableHeight = d.intrinsicHeight
    if (drawableWidth <= 0 || drawableHeight <= 0) return

    val scale = maxOf(
        viewWidth.toFloat() / drawableWidth.toFloat(),
        viewHeight.toFloat() / drawableHeight.toFloat()
    )

    val scaledWidth = drawableWidth * scale
    val dx = (viewWidth - scaledWidth) * 0.5f

    val matrix = Matrix().apply {
        setScale(scale, scale)
        postTranslate(dx, 0f)
    }

    scaleType = ImageView.ScaleType.MATRIX
    imageMatrix = matrix
}

