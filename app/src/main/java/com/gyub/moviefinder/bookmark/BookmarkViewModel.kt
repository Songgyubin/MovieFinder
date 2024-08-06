package com.gyub.moviefinder.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetBookmarkedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 북마크 뷰모델
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _bookmarkedMovies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val bookmarkedMovies = _bookmarkedMovies.asStateFlow()

    init {
        viewModelScope.launch {
            getBookmarkedMoviesUseCase()
                .cachedIn(viewModelScope)
                .collect {
                    _bookmarkedMovies.value = it
                }
        }
    }

    fun onDeleteBookmarkedMovie(movie: MovieModel) {
        flow {
            emit(bookmarkMovieUseCase(movie, false))
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