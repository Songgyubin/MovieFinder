package com.gyub.feature.home.model

import com.gyub.core.domain.model.MovieModel

/**
 * 영화 UI State
 *
 * @author   Gyub
 * @created  2024/09/04
 */
sealed interface MovieUiState {
    data object Loading : MovieUiState

    data class Success(val movies: List<MovieModel>) : MovieUiState

    data class Error(val message: String) : MovieUiState
}