package com.gyub.feature.detail

import app.cash.turbine.test
import com.gyub.core.domain.model.MovieCreditsModel
import com.gyub.core.domain.model.MovieDetailModel
import com.gyub.core.domain.model.MovieModel
import com.gyub.core.domain.usecase.GetBookmarkedMovieIdsUseCase
import com.gyub.core.domain.usecase.GetMovieCreditsUseCase
import com.gyub.core.domain.usecase.GetMovieDetailUseCase
import com.gyub.core.domain.usecase.GetSimilarMoviesUseCase
import com.gyub.core.testing.rule.MainDispatcherRule
import com.gyub.feature.detail.model.MovieDetailUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * 영화 상세 뷰모델 테스트
 *
 * @author   Gyub
 * @created  2024/08/20
 */
class MovieDetailViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getMovieDetailUseCase = mockk<GetMovieDetailUseCase>()
    private val getMovieCreditsUseCase = mockk<GetMovieCreditsUseCase>()
    private val getBookmarkedMovieIdsUseCase = mockk<GetBookmarkedMovieIdsUseCase>()
    private val getSimilarMoviesUseCase = mockk<GetSimilarMoviesUseCase>()

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private val testMovieId = 52237

    @Before
    fun setup() {
        coEvery { getMovieDetailUseCase(testMovieId) } returns flowOf(fakeMovieDetail)
        coEvery { getMovieCreditsUseCase(testMovieId) } returns flowOf(fakeCredits)
        coEvery { getBookmarkedMovieIdsUseCase() } returns flowOf(fakeBookmarkedIds)
        coEvery { getSimilarMoviesUseCase(testMovieId) } returns flowOf(fakeSimilarMovies)

        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailUseCase,
            getMovieCreditsUseCase,
            getBookmarkedMovieIdsUseCase,
            getSimilarMoviesUseCase
        )
    }

    @Test
    fun `영화 상세 정보를 가져온다`() = runTest {
        movieDetailViewModel.fetchMovieDetail(testMovieId)

        movieDetailViewModel.movieDetailUiState.test {
            val successState = awaitItem() as MovieDetailUiState.Success

            assert(successState.movieDetail.id == fakeMovieDetail.id)
            assert(successState.director == fakeCredits.getDirector())
            assert(successState.cast == fakeCredits.cast)
            assert(successState.movieDetail.isBookmarked == fakeBookmarkedIds.contains(fakeMovieDetail.id))
        }
    }

    companion object {
        private val fakeMovieDetail = MovieDetailModel(
            id = 2249,
            title = "test title",
            overview = "test overview",
            genres = listOf(),
            voteAverage = 2.3,
            voteCount = 2720,
            runtime = 7237,
            releaseDate = "audire",
            posterUrl = "http://www.bing.com/search?q=vidisse",
            isBookmarked = false
        )

        private val fakeCredits = MovieCreditsModel(
            crew = listOf(
                MovieCreditsModel.CrewMemberModel(
                    id = 6516,
                    job = "Director",
                    name = "test name",
                    profilePath = "himenaeos"
                )
            ),
            cast = listOf(
                MovieCreditsModel.CastMemberModel(
                    id = 1917,
                    name = "test cast name1",
                    character = "ocurreret",
                    profilePath = "noluisse"
                ),
                MovieCreditsModel.CastMemberModel(
                    id = 1918,
                    name = "test cast name2",
                    character = "ocurreret",
                    profilePath = "noluisse"
                )
            )
        )
        private val fakeBookmarkedIds = listOf(
            2249,
            1234
        )
        private val fakeSimilarMovies = listOf(
            MovieModel(
                id = 2249,
                title = "test title 1",
            ),
            MovieModel(
                id = 2250,
                title = "test title 2",
            )
        )
    }
}