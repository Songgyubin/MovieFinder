package com.gyub.core.data.di

import com.gyub.core.data.datasource.MovieDataSource
import com.gyub.core.data.datasource.remote.MovieRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Data Source Module
 *
 * @author   Gyub
 * @created  2024/08/18
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsMovieDataSource(movieRemoteDataSource: MovieRemoteDataSource): MovieDataSource
}