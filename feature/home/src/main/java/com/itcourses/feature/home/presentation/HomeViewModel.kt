package com.itcourses.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itcourses.core.common.AppResult
import com.itcourses.core.common.model.Course
import com.itcourses.core.common.usecase.GetCoursesUseCase
import com.itcourses.core.common.usecase.ToggleFavoriteUseCase
import com.itcourses.feature.home.presentation.model.CourseUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCourses: GetCoursesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {

    private val allCourses = MutableStateFlow<List<Course>>(emptyList())

    private val _state = MutableStateFlow(HomeUiState(isLoading = true))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCourses().collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        allCourses.value = result.value
                        _state.update {
                            it.copy(
                                isLoading = false,
                                items = mapCourses(result.value, it.query, it.sortAscending),
                                errorMessage = null,
                            )
                        }
                    }
                    is AppResult.Error -> _state.update {
                        it.copy(isLoading = false, errorMessage = result.throwable.message ?: "Ошибка")
                    }
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        _state.update {
            it.copy(
                query = query,
                items = mapCourses(allCourses.value, query, it.sortAscending),
            )
        }
    }

    fun onToggleFavorite(courseId: Long) {
        viewModelScope.launch {
            toggleFavorite(courseId).collect { /* ignore */ }
        }
    }

    fun onToggleSort() {
        _state.update {
            val newSortAsc = !it.sortAscending
            it.copy(
                sortAscending = newSortAsc,
                items = mapCourses(allCourses.value, it.query, newSortAsc),
            )
        }
    }

    private fun mapCourses(courses: List<Course>, query: String, sortAscending: Boolean): List<CourseUiModel> {
        val q = query.trim().lowercase()
        val filtered = if (q.isEmpty()) courses else courses.filter {
            it.title.lowercase().contains(q) || it.description.lowercase().contains(q)
        }

        val sorted = filtered.sortedWith { a, b ->
            val left = a.publishDate
            val right = b.publishDate
            if (sortAscending) left.compareTo(right) else right.compareTo(left)
        }

        return sorted.map {
            CourseUiModel(
                id = it.id,
                title = it.title,
                description = it.description,
                price = it.price,
                isFavorite = it.isFavorite,
                rate = it.rate,
                startDateIso = it.startDate,
                publishDateIso = it.publishDate,
            )
        }
    }
}

