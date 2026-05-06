package com.itcourses.feature.home.presentation.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.itcourses.core.ui.util.applyTopCrop
import com.itcourses.feature.home.databinding.ItemCourseBinding
import com.itcourses.feature.home.presentation.model.CourseUiModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun courseItemDelegate(
    onCourseClick: (CourseUiModel) -> Unit,
    onToggleFavorite: (CourseUiModel) -> Unit,
) = adapterDelegateViewBinding<CourseUiModel, CourseUiModel, ItemCourseBinding>(
    viewBinding = { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) },
) {
    binding.root.setOnClickListener { onCourseClick(item) }
    binding.details.setOnClickListener { onCourseClick(item) }
    binding.bookmark.setOnClickListener { onToggleFavorite(item) }

    bind {
        binding.title.text = item.title
        binding.description.text = item.description
        binding.price.text = "${item.price} ₽"
        binding.rate.text = item.rate
        binding.startDate.text = item.startDateIso.toUiRuDate()
        binding.image.setImageResource(com.itcourses.core.ui.R.drawable.course_placeholder)
        binding.image.post { binding.image.applyTopCrop() }
        binding.bookmark.setImageResource(
            if (item.isFavorite) com.itcourses.core.ui.R.drawable.ic_bookmark_filled
            else com.itcourses.core.ui.R.drawable.ic_bookmark_outline
        )
    }
}

private fun String.toUiRuDate(): String {
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = input.parse(this) ?: return this
        val output = SimpleDateFormat("d MMMM yyyy", Locale("ru")).apply {
            timeZone = TimeZone.getDefault()
        }
        output.format(date)
    } catch (_: Throwable) {
        this
    }
}

