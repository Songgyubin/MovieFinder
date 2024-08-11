package com.gyub.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
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

    private val _movies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val movies = _movies.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable?>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        getMovies()
    }

    private fun getMovies(orderBy: String = "now_playing") {
        viewModelScope.launch {
            getMoviesUseCase(orderBy = orderBy).cachedIn(viewModelScope)
                .combine(getBookmarkedMovieIdsUseCase()) { movies, bookmarkedMovieIds ->
                    movies.map {
                        it.copy(
                            isBookmarked = bookmarkedMovieIds.contains(it.id)
                        )
                    }
                }.collect {
                    _movies.value = it
                }
        }
    }

    fun onBookmarkMovie(movie: MovieModel) {
        val bookmark = movie.isBookmarked.not()

        flow {
            emit(bookmarkMovieUseCase(movie, bookmark))
        }.catch {
            _errorFlow.emit(it)
        }.launchIn(viewModelScope)
    }

    fun notifyErrorMessage(throwable: Throwable) {
        viewModelScope.launch {
            _errorFlow.emit(throwable)
        }
    }
}