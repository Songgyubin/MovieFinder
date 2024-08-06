package com.gyub.core.db.di

import com.gyub.core.db.AppDatabase
import com.gyub.core.db.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Daos Module
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesBookmarkDao(
        database: AppDatabase,
    ): BookmarkDao = database.bookmarkDao()
}