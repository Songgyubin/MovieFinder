package com.gyub.moviefinder.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
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
            getMoviesUseCase(orderBy = orderBy)
                .cachedIn(viewModelScope)
                .collect {
                    _movies.value = it
                }
        }
    }

    fun notifyErrorMessage(throwable: Throwable) {
        viewModelScope.launch {
            _errorFlow.emit(throwable)
        }
    }
}