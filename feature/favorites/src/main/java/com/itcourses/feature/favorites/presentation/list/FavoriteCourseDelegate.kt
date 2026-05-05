package com.itcourses.feature.favorites.presentation.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.itcourses.feature.favorites.databinding.ItemCourseBinding
import com.itcourses.feature.favorites.presentation.model.CourseUiModel

fun favoriteCourseDelegate(
    onCourseClick: (CourseUiModel) -> Unit,
    onUnfavorite: (CourseUiModel) -> Unit,
) = adapterDelegateViewBinding<CourseUiModel, CourseUiModel, ItemCourseBinding>(
    viewBinding = { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) },
) {
    binding.root.setOnClickListener { onCourseClick(item) }
    binding.like.setOnClickListener { onUnfavorite(item) }

    bind {
        binding.title.text = item.title
        binding.description.text = item.description
        binding.price.text = "${item.price} ₽"
    }
}

