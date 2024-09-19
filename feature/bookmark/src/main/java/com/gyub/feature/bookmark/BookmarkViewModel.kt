package com.gyub.feature.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetBookmarkedMoviesUseCase
import com.gyub.core.ui.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {
    val bookmarkedMovies: StateFlow<PagingData<MovieModel>> = getBookmarkedMoviesUseCase()
        .onStart { PagingData.empty<MovieModel>() }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )

    fun onDeleteBookmarkedMovie(movie: MovieModel) {
        flow {
            emit(bookmarkMovieUseCase(movie, false))
            SnackbarController.sendEvent(R.string.feature_bookmark_unbookmarked)
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