package com.itcourses.feature.profile.presentation

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
import com.itcourses.feature.profile.R
import com.itcourses.feature.profile.databinding.FragmentProfileBinding
import com.itcourses.feature.profile.presentation.list.myCourseDelegate
import com.itcourses.feature.profile.presentation.model.MyCourseUiModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val vm: ProfileViewModel by viewModel()

    private val adapter = ListDelegationAdapter(
        myCourseDelegate(onCourseClick = { navigateToCourse(it) })
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    adapter.items = state.myCourses
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun navigateToCourse(course: MyCourseUiModel) {
        findNavController().navigate(
            R.id.courseFragment,
            bundleOf("courseId" to course.id.toString()),
        )
    }
}

