package com.itcourses.feature.course.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itcourses.core.common.AppResult
import com.itcourses.core.common.usecase.GetCourseUseCase
import com.itcourses.core.common.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel(
    private val getCourse: GetCourseUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CourseUiState(isLoading = true))
    val state: StateFlow<CourseUiState> = _state.asStateFlow()

    private var loadJob: Job? = null

    fun load(courseId: Long) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            getCourse(courseId).collect { result ->
                when (result) {
                    is AppResult.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            title = result.value.title,
                            description = result.value.description,
                            rate = result.value.rate,
                            startDateIso = result.value.startDate,
                            isFavorite = result.value.isFavorite,
                            errorMessage = null,
                        )
                    }
                    is AppResult.Error -> _state.update {
                        it.copy(isLoading = false, errorMessage = result.throwable.message ?: "Ошибка")
                    }
                }
            }
        }
    }

    fun onToggleFavorite(courseId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isFavorite = !it.isFavorite) }
            toggleFavorite(courseId).collect { /* ignore */ }
        }
    }
}

