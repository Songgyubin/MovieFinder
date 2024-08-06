package com.gyub.core.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gyub.core.db.model.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * 북마크 Dao
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarked_movies")
    fun getBookmarkedMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT id FROM bookmarked_movies")
    fun getBookmarkedMovieIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedMovie(movie: MovieEntity)

    @Query("DELETE FROM bookmarked_movies WHERE id = :id")
    suspend fun deleteBookmarkedMovie(id: Int)
}