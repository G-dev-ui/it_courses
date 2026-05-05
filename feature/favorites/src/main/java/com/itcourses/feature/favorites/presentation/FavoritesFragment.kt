package com.itcourses.feature.favorites.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.itcourses.core.ui.base.BaseFragment
import com.itcourses.feature.favorites.R
import com.itcourses.feature.favorites.databinding.FragmentFavoritesBinding
import com.itcourses.feature.favorites.presentation.list.favoriteCourseDelegate
import com.itcourses.feature.favorites.presentation.model.CourseUiModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {
    private val vm: FavoritesViewModel by viewModel()

    private val adapter = ListDelegationAdapter(
        favoriteCourseDelegate(
            onCourseClick = { navigateToCourse(it) },
            onUnfavorite = { vm.onUnfavorite(it.id) },
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    adapter.items = state.items
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun navigateToCourse(course: CourseUiModel) {
        findNavController().navigate(
            R.id.courseFragment,
            bundleOf("courseId" to course.id.toString()),
        )
    }
}

