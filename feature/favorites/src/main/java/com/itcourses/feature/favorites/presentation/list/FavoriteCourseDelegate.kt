package com.itcourses.feature.favorites.presentation.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.itcourses.core.ui.util.applyTopCrop
import com.itcourses.feature.favorites.databinding.ItemFavoriteCourseBinding
import com.itcourses.feature.favorites.presentation.model.CourseUiModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun favoriteCourseDelegate(
    onCourseClick: (CourseUiModel) -> Unit,
    onUnfavorite: (CourseUiModel) -> Unit,
) = adapterDelegateViewBinding<CourseUiModel, CourseUiModel, ItemFavoriteCourseBinding>(
    viewBinding = { inflater, parent -> ItemFavoriteCourseBinding.inflate(inflater, parent, false) },
) {
    binding.root.setOnClickListener { onCourseClick(item) }
    binding.bookmark.setOnClickListener { onUnfavorite(item) }

    bind {
        binding.title.text = item.title
        binding.description.text = item.description
        binding.price.text = "${item.price} ₽"
        binding.rate.text = item.rate
        binding.startDate.text = item.startDateIso.toUiRuDate()
        binding.image.setImageResource(com.itcourses.core.ui.R.drawable.course_placeholder)
        binding.image.post { binding.image.applyTopCrop() }
        binding.bookmark.setImageResource(com.itcourses.core.ui.R.drawable.ic_bookmark_filled)
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

