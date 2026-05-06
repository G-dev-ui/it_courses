package com.itcourses.feature.profile.presentation.list

import android.graphics.Matrix
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.itcourses.feature.profile.databinding.ItemMyCourseBinding
import com.itcourses.feature.profile.presentation.model.MyCourseUiModel

fun myCourseDelegate(
    onCourseClick: (MyCourseUiModel) -> Unit,
) = adapterDelegateViewBinding<MyCourseUiModel, MyCourseUiModel, ItemMyCourseBinding>(
    viewBinding = { inflater, parent -> ItemMyCourseBinding.inflate(inflater, parent, false) },
) {
    binding.root.setOnClickListener { onCourseClick(item) }

    bind {
        binding.title.text = item.title
        binding.progress.progress = item.progressPercent
        binding.progressText.text = "${item.progressPercent}%"
        binding.lessonsText.text = lessonsSpannable(
            done = item.lessonsDone,
            total = item.lessonsTotal,
        )

        binding.image.setImageResource(com.itcourses.core.ui.R.drawable.course_placeholder)
        binding.image.post { applyTopCrop(binding.image) }

        binding.bookmark.setImageResource(
            if (item.isFavorite) com.itcourses.core.ui.R.drawable.ic_bookmark_filled
            else com.itcourses.core.ui.R.drawable.ic_bookmark_outline
        )
    }
}

private fun lessonsSpannable(done: Int, total: Int): SpannableString {
    val doneSafe = done.coerceIn(0, total)
    val doneText = "$doneSafe"
    val totalText = "$total"
    val full = "$doneText/$totalText уроков"
    return SpannableString(full).apply {
        val accent = ForegroundColorSpan(0xFF12B956.toInt())
        val secondary50 = ForegroundColorSpan(0x80F2F2F3.toInt())

        setSpan(accent, 0, doneText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(secondary50, doneText.length, full.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

private fun applyTopCrop(imageView: ImageView) {
    val d = imageView.drawable ?: return
    val viewWidth = imageView.width - imageView.paddingLeft - imageView.paddingRight
    val viewHeight = imageView.height - imageView.paddingTop - imageView.paddingBottom
    if (viewWidth <= 0 || viewHeight <= 0) return

    val drawableWidth = d.intrinsicWidth
    val drawableHeight = d.intrinsicHeight
    if (drawableWidth <= 0 || drawableHeight <= 0) return

    val scale = maxOf(
        viewWidth.toFloat() / drawableWidth.toFloat(),
        viewHeight.toFloat() / drawableHeight.toFloat()
    )

    val scaledWidth = drawableWidth * scale
    val scaledHeight = drawableHeight * scale

    val dx = (viewWidth - scaledWidth) * 0.5f
    val dy = 0f // top-crop: не центрируем по Y

    val matrix = Matrix()
    matrix.setScale(scale, scale)
    matrix.postTranslate(dx, dy)
    imageView.scaleType = ImageView.ScaleType.MATRIX
    imageView.imageMatrix = matrix
}

