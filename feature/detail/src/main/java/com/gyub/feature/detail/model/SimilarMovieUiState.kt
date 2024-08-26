package com.gyub.feature.detail.model

import com.gyub.core.domain.model.MovieModel
import kotlinx.collections.immutable.PersistentList

/**
 * 유사 영화 UI State
 *
 * @author   Gyub
 * @created  2024/08/30
 */
sealed interface SimilarMovieUiState {
    data object Error : SimilarMovieUiState

    data object Loading : SimilarMovieUiState

    data class Success(
        val movies: PersistentList<MovieModel>,
    ) : SimilarMovieUiState
}