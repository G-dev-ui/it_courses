package com.itcourses.feature.home.presentation.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.itcourses.feature.home.databinding.ItemCourseBinding
import com.itcourses.feature.home.presentation.model.CourseUiModel

fun courseItemDelegate(
    onCourseClick: (CourseUiModel) -> Unit,
    onToggleFavorite: (CourseUiModel) -> Unit,
) = adapterDelegateViewBinding<CourseUiModel, CourseUiModel, ItemCourseBinding>(
    viewBinding = { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) },
) {
    binding.root.setOnClickListener { onCourseClick(item) }
    binding.details.setOnClickListener { onCourseClick(item) }
    binding.like.setOnClickListener { onToggleFavorite(item) }

    bind {
        binding.title.text = item.title
        binding.description.text = item.description
        binding.price.text = "${item.price} ₽"
        binding.like.setImageResource(
            if (item.isFavorite) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
        )
    }
}

