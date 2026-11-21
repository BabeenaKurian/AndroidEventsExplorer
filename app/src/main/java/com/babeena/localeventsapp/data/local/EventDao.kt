package com.babeena.localeventsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(event: List<EventEntity>)

    @Query("SELECT * FROM events ORDER By dateTime ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity?

    @Transaction
    suspend fun replaceAllEvents(events: List<EventEntity>) {
        deleteAllEvents()
        insertEvents(events)
    }

}