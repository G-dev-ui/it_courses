package com.itcourses.feature.profile.presentation.list

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
    }
}

