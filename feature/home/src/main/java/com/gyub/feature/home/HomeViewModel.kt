@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gyub.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMoviesUseCase
import com.gyub.core.model.MovieListType
import com.gyub.core.ui.SnackbarController
import com.gyub.feature.home.model.MovieSection
import com.gyub.feature.home.model.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 홈 뷰모델
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getBookmarkedMovieIdsUseCase: GetBookmarkedMovieIdsUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {

    val movies: StateFlow<MovieUiState> = MovieListType.entries
        .asFlow()
        .flatMapMerge { movieListType ->
            getMoviesUseCase(movieListType.orderBy)
                .combine(getBookmarkedMovieIdsUseCase()) { movies, bookmarkedMovieIds ->
                    MovieSection(
                        movies = movies.map { movie ->
                            movie.copy(isBookmarked = bookmarkedMovieIds.contains(movie.id))
                        },
                        movieListType = movieListType
                    )
                }
        }
        .runningFold(emptyList<MovieSection>()) { acc, movieSection ->
            acc + movieSection
        }
        .map { movieSections ->
            MovieUiState.Success(movieSections)
        }
        .onStart { MovieUiState.Loading}
        .catch { throwable ->
            MovieUiState.Error(throwable.message ?: "Unknown Error")
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MovieUiState.Loading
        )

    fun onBookmarkMovie(movie: MovieModel) {
        val bookmark = movie.isBookmarked.not()

        val messageId = if (bookmark) {
            R.string.feature_home_bookmarked
        } else {
            R.string.feature_home_unbookmarked
        }

        flow {
            emit(bookmarkMovieUseCase(movie, bookmark))
            SnackbarController.sendEvent(messageId)
        }.catch {
            SnackbarController.sendEvent(it)
        }.launchIn(viewModelScope)
    }

    fun notifyErrorMessage(throwable: Throwable) {
        viewModelScope.launch {
            SnackbarController.sendEvent(throwable)
        }
    }
}