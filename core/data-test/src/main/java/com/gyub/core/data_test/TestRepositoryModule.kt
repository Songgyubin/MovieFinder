package com.gyub.core.data_test

import com.gyub.core.data.datasource.MovieDataSource
import com.gyub.core.data.di.DataSourceModule
import com.gyub.core.data_test.datasource.FakeMovieDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/18
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSourceModule::class]
)
interface TestRepositoryModule {

    @Binds
    fun bindsFakeMovieDataSource(fakeMovieDataSource: FakeMovieDataSource): MovieDataSource
}