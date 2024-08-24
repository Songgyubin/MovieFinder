package com.gyub.core.network.model

/**
 * 영화 상세 Response
 *
 * @author   Gyub
 * @created  2024/08/18
 */
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionResponse?,
    val budget: Long,
    val genres: List<GenreResponse>,
    val homepage: String,
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyResponse>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryResponse>,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageResponse>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
) {
    @Serializable
    data class CollectionResponse(
        val id: Int,
        val name: String,
        @SerialName("poster_path")
        val posterPath: String,
        @SerialName("backdrop_path")
        val backdropPath: String,
    )

    @Serializable
    data class GenreResponse(
        val id: Int,
        val name: String,
    )

    @Serializable
    data class ProductionCompanyResponse(
        val id: Int,
        @SerialName("logo_path")
        val logoPath: String?,
        val name: String,
        @SerialName("origin_country")
        val originCountry: String,
    )

    @Serializable
    data class ProductionCountryResponse(
        @SerialName("iso_3166_1")
        val iso31661: String,
        val name: String,
    )

    @Serializable
    data class SpokenLanguageResponse(
        @SerialName("english_name")
        val englishName: String,
        @SerialName("iso_639_1")
        val iso6391: String,
        val name: String,
    )
}