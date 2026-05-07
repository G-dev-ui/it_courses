package com.itcourses.feature.home.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.itcourses.core.ui.base.BaseFragment
import com.itcourses.feature.home.databinding.FragmentHomeBinding
import com.itcourses.feature.home.presentation.list.courseItemDelegate
import com.itcourses.feature.home.presentation.model.CourseUiModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val vm: HomeViewModel by viewModel()

    private val adapter = ListDelegationAdapter(
        courseItemDelegate(
            onCourseClick = { navigateToCourse(it) },
            onToggleFavorite = { vm.onToggleFavorite(it.id) },
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        binding.search.doOnTextChanged { text, _, _, _ ->
            vm.onQueryChanged(text?.toString().orEmpty())
        }

        binding.sort.setOnClickListener { vm.onToggleSort() }

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
        findNavController().navigate(Uri.parse("courses://course/${course.id}"))
    }
}

