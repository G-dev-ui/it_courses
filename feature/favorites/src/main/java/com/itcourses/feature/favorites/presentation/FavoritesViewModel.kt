package com.itcourses.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itcourses.core.common.AppResult
import com.itcourses.core.common.usecase.GetFavoritesUseCase
import com.itcourses.core.common.usecase.ToggleFavoriteUseCase
import com.itcourses.feature.favorites.presentation.model.CourseUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavorites: GetFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesUiState(isLoading = true))
    val state: StateFlow<FavoritesUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getFavorites().collect { result ->
                when (result) {
                    is AppResult.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            items = result.value.map { c ->
                                CourseUiModel(c.id, c.title, c.description, c.price)
                            },
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

    fun onUnfavorite(courseId: Long) {
        viewModelScope.launch {
            toggleFavorite(courseId).collect()
        }
    }
}

