package com.gyub.feature.home.model

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.model.MovieListType

/**
 * 영화 UI State
 *
 * @author   Gyub
 * @created  2024/09/04
 */
sealed interface MovieUiState {
    data object Loading : MovieUiState

    data class Success(val movieSections: List<MovieSection>) : MovieUiState

    data class Error(val message: String) : MovieUiState
}

data class MovieSection(
    val movies: List<MovieModel>,
    val movieListType: MovieListType,
)