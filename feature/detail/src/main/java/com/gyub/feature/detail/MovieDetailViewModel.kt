@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gyub.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMovieCreditsUseCase
import com.gyub.core.domain.usecase.GetMovieDetailUseCase
import com.gyub.core.domain.usecase.GetRecommendationMoviesUseCase
import com.gyub.core.domain.usecase.GetSimilarMoviesUseCase
import com.gyub.core.model.MovieFinderResult
import com.gyub.core.model.asResult
import com.gyub.core.ui.SnackbarController
import com.gyub.core.ui.toUiText
import com.gyub.feature.detail.model.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 영화 상세 뷰모델
 *
 * @author   Gyub
 * @created  2024/08/20
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getBookmarkedMovieIdsUseCase: GetBookmarkedMovieIdsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getRecommendationMoviesUseCase: GetRecommendationMoviesUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId = savedStateHandle.getStateFlow("movieId", 0)

    val movieDetailUiState: StateFlow<MovieDetailUiState> = movieId.flatMapLatest { id ->
        combine(
            getMovieDetailUseCase(id),
            getMovieCreditsUseCase(id),
            getSimilarMoviesUseCase(id),
            getRecommendationMoviesUseCase(id),
            getBookmarkedMovieIdsUseCase()
        ) { detail, credits, similarMovies, recommendationMovies, bookmarkedMovieIds ->

            MovieDetailUiState.Success(
                movieDetail = detail.copy(
                    isBookmarked = bookmarkedMovieIds.contains(detail.id)
                ),
                director = credits.getDirector(),
                cast = credits.cast.toPersistentList(),
                similarMovies = similarMovies.toPersistentList(),
                recommendationMovies = recommendationMovies.toPersistentList()
            )
        }.asResult()
    }.map { result ->
        when (result) {
            is MovieFinderResult.Error -> {
                MovieDetailUiState.Error(result.exception.toUiText())
            }

            MovieFinderResult.Loading -> {
                MovieDetailUiState.Loading
            }

            is MovieFinderResult.Success -> {
                result.data
            }
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieDetailUiState.Loading
    )

    fun notifyErrorMessage(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(message = message)
        }
    }
}