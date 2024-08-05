package com.gyub.core.network.di

import com.gyub.core.network.retrofit.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

/**
 * Service 모듈
 *
 * @author   Gyub
 * @created  2024/08/05
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService =
        retrofit.create()
}