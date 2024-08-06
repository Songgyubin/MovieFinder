package com.gyub.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gyub.core.db.dao.BookmarkDao
import com.gyub.core.db.model.MovieEntity

/**
 * Data base
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}