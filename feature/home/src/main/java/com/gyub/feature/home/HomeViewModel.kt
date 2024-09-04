package com.gyub.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMoviesUseCase
import com.gyub.core.ui.SnackbarController
import com.gyub.feature.home.model.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
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
    private val _movies = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movies = _movies.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies(orderBy: String = "now_playing") {
        viewModelScope.launch {
            getMoviesUseCase(orderBy = orderBy)
                .combine(getBookmarkedMovieIdsUseCase()) { movies, bookmarkedMovieIds ->
                    movies.map {
                        it.copy(
                            isBookmarked = bookmarkedMovieIds.contains(it.id)
                        )
                    }
                }.onStart {
                    _movies.value = MovieUiState.Loading
                }.catch {
                    _movies.value = MovieUiState.Error(it.message ?: "Unknown Error")
                }.collect {
                    _movies.value = MovieUiState.Success(it)
                }
        }
    }

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