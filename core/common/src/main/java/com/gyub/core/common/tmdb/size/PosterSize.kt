package com.gyub.core.common.tmdb.size

/**
 * TMDB API에 포맷에 맞는 각 이미지 종류별 SIZE
 *
 * TMDB 특성 상 응답으로 오는 poster_path(파일명)에 BaseUrl을 붙여서
 * 이미지를 받아와야 함
 *
 * https://image.tmdb.org/t/p/w200(이미지 크기)/파일명
 *
 * @author   Gyub
 * @created  2024/08/06
 */
enum class BackdropSize(override val size: String) : TmdbImageSize {
    W300("w300"),
    W780("w780"),
    W1280("w1280"),
    ORIGINAL("original");

    override fun toString(): String = size
}

enum class LogoSize(override val size: String) : TmdbImageSize  {
    W45("w45"),
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W300("w300"),
    W500("w500"),
    ORIGINAL("original");

    override fun toString(): String = size
}

enum class PosterSize(override val size: String) : TmdbImageSize  {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORIGINAL("original");

    override fun toString(): String = size
}

enum class ProfileSize(override val size: String) : TmdbImageSize  {
    W45("w45"),
    W185("w185"),
    H632("h632"),
    ORIGINAL("original");

    override fun toString(): String = size
}

enum class StillSize(override val size: String) : TmdbImageSize  {
    W92("w92"),
    W185("w185"),
    W300("w300"),
    ORIGINAL("original");

    override fun toString(): String = size
}