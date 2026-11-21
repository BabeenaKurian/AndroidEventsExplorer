package com.babeena.localeventsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookMarkedEntity: BookMarkEntity)

    @Query("SELECT * FROM bookmarked_events ORDER BY title ASC")
    fun getAllBookMarkedItems(): Flow<List<BookMarkEntity>>

    @Query("DELETE FROM bookmarked_events WHERE id = :eventId")
    suspend fun removeBookmark(eventId: String)
}