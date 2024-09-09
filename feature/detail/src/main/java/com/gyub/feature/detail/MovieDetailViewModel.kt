package com.gyub.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMovieCreditsUseCase
import com.gyub.core.domain.usecase.GetMovieDetailUseCase
import com.gyub.core.domain.usecase.GetRecommendationMoviesUseCase
import com.gyub.core.domain.usecase.GetSimilarMoviesUseCase
import com.gyub.core.model.Result
import com.gyub.core.model.asResult
import com.gyub.core.ui.SnackbarController
import com.gyub.core.ui.toUiText
import com.gyub.feature.detail.model.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
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
) : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val movieDetailUiState = _movieDetailUiState.asStateFlow()

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            combine(
                getMovieDetailUseCase(movieId),
                getMovieCreditsUseCase(movieId),
                getSimilarMoviesUseCase(movieId),
                getRecommendationMoviesUseCase(movieId),
                getBookmarkedMovieIdsUseCase()
            ) { detail, credits, similarMovies, recommendationMovies, bookmarkedMovieIds ->

                MovieDetailUiState.Success(
                    movieDetail = detail.copy(
                        isBookmarked = bookmarkedMovieIds.contains(detail.id)
                    ),
                    director = credits.getDirector(),
                    cast = credits.cast,
                    similarMovies = similarMovies.toPersistentList(),
                    recommendationMovies = recommendationMovies.toPersistentList()
                )
            }.asResult()
                .onEach { result ->
                    when (result) {
                        is Result.Error -> {
                            _movieDetailUiState.value = MovieDetailUiState.Error(result.exception.toUiText())
                        }

                        Result.Loading -> {
                            _movieDetailUiState.value = MovieDetailUiState.Loading
                        }

                        is Result.Success -> {
                            _movieDetailUiState.value = result.data
                        }
                    }
                }.collect()
        }
    }

    fun notifyErrorMessage(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(message = message)
        }
    }
}