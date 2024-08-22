package com.gyub.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMovieCreditsUseCase
import com.gyub.core.domain.usecase.GetMovieDetailUseCase
import com.gyub.feature.detail.model.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
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
) : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val movieDetailUiState = _movieDetailUiState.asStateFlow()

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            combine(
                getMovieDetailUseCase(movieId),
                getMovieCreditsUseCase(movieId),
                getBookmarkedMovieIdsUseCase()
            ) { detail, credits, bookmarkedMovieIds ->

                MovieDetailUiState.Success(
                    movieDetail = detail.copy(
                        isBookmarked = bookmarkedMovieIds.contains(detail.id)
                    ),
                    movieCredits = credits
                )
            }.onStart { _movieDetailUiState.value = MovieDetailUiState.Loading }
                .collect { _movieDetailUiState.value = it }
        }
    }
}