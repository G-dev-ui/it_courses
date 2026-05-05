package com.itcourses.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itcourses.core.common.AppResult
import com.itcourses.core.common.usecase.GetCoursesUseCase
import com.itcourses.feature.profile.presentation.model.MyCourseUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCourses: GetCoursesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState(isLoading = true))
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCourses().collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        val my = result.value
                            .take(2)
                            .map { c ->
                                MyCourseUiModel(
                                    id = c.id,
                                    title = c.title,
                                    progressPercent = ((c.id % 100).toInt().coerceIn(5, 95)),
                                )
                            }
                        _state.update { it.copy(isLoading = false, myCourses = my, errorMessage = null) }
                    }
                    is AppResult.Error -> _state.update {
                        it.copy(isLoading = false, errorMessage = result.throwable.message ?: "Ошибка")
                    }
                }
            }
        }
    }
}

