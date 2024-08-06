package com.gyub.core.data.di

import com.gyub.core.data.repository.MovieRepositoryImpl
import com.gyub.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Repository Module
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}