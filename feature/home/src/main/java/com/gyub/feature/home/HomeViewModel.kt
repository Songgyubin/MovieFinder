package com.gyub.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.BookmarkMovieUseCase
import com.gyub.core.domain.usecase.GetMoviesUseCase
import com.gyub.core.model.MovieFinderResult
import com.gyub.core.model.MovieListType
import com.gyub.core.ui.SnackbarController
import com.gyub.core.ui.toUiText
import com.gyub.feature.home.model.MovieSectionData
import com.gyub.feature.home.model.SectionUiState
import com.gyub.feature.home.model.SectionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {
    private val _sectionsState = MutableStateFlow(SectionsState())
    val sections = _sectionsState.asStateFlow()

    init {
        fetchAllSections()
    }

    private fun fetchAllSections() {
        MovieListType.entries.forEach { movieListType ->
            loadMovies(movieListType)
        }
    }

    private fun loadMovies(movieListType: MovieListType) {
        viewModelScope.launch {
            getMoviesUseCase(movieListType.orderBy)
                .collect { result ->
                    when (result) {
                        is MovieFinderResult.Error -> {
                            _sectionsState.value = _sectionsState.value.copy(
                                sections = _sectionsState.value.sections.put(
                                    movieListType,
                                    SectionUiState.Error(result.exception.toUiText())
                                )
                            )
                        }

                        MovieFinderResult.Loading -> {
                            _sectionsState.value = _sectionsState.value.copy(
                                sections = _sectionsState.value.sections.put(movieListType, SectionUiState.Loading)
                            )
                        }

                        is MovieFinderResult.Success -> {
                            val movieSectionData = MovieSectionData(
                                movies = result.data.toPersistentList(),
                                movieListType = movieListType
                            )
                            _sectionsState.value = _sectionsState.value.copy(
                                sections = _sectionsState.value.sections.put(
                                    movieListType,
                                    SectionUiState.Success(movieSectionData)
                                )
                            )
                        }
                    }
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

    fun notifyErrorMessage(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(message = message)
        }
    }
}