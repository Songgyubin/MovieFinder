package com.gyub.feature.home.model

import com.gyub.core.domain.model.MovieModel
import com.gyub.core.model.MovieListType
import com.gyub.core.ui.UiText
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

/**
 * 영화 UI State
 *
 * @author   Gyub
 * @created  2024/09/04
 */
data class SectionsState(
    val sections: PersistentMap<MovieListType, SectionUiState> = persistentMapOf(),
)

sealed interface SectionUiState {
    data object Loading : SectionUiState

    data class Success(val movieSectionData: MovieSectionData) : SectionUiState

    data class Error(val uiText: UiText) : SectionUiState
}

data class MovieSectionData(
    val movies: List<MovieModel>,
    val movieListType: MovieListType,
)
