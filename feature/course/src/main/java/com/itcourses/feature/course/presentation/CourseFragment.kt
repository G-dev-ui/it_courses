package com.itcourses.feature.course.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.itcourses.core.ui.base.BaseFragment
import com.itcourses.feature.course.databinding.FragmentCourseBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class CourseFragment : BaseFragment<FragmentCourseBinding>(FragmentCourseBinding::inflate) {
    private val vm: CourseViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseId = arguments?.getString("courseId")?.toLongOrNull() ?: return
        vm.load(courseId)

        binding.back.setOnClickListener { findNavController().popBackStack() }
        binding.favorite.setOnClickListener { vm.onToggleFavorite(courseId) }
        binding.start.setOnClickListener { /* старт курса */ }
        binding.openPlatform.setOnClickListener { /* открыть платформу */ }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    binding.title.text = state.title
                    binding.about.text = state.description
                    binding.rate.text = state.rate
                    binding.startDate.text = state.startDateIso.toUiRuDate()
                    binding.image.setImageResource(com.itcourses.core.ui.R.drawable.course_placeholder)
                    binding.favorite.setImageResource(
                        if (state.isFavorite) com.itcourses.core.ui.R.drawable.ic_bookmark_filled_black
                        else com.itcourses.core.ui.R.drawable.ic_bookmark_outline_black,
                    )
                }
            }
        }
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

