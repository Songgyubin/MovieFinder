package com.gyub.core.data_test

import com.gyub.core.data.di.RepositoryModule
import com.gyub.core.data_test.repository.FakeMovieRepository
import com.gyub.core.domain.repository.MovieRepository
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
    replaces = [RepositoryModule::class]
)
interface TestRepositoryModule {

    @Binds
    fun bindsFakeMovieRepository(fakeMovieRepository: FakeMovieRepository): MovieRepository
}