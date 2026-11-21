package com.babeena.localeventsapp.domain.repository

import com.babeena.localeventsapp.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {

    suspend fun addBookMarkedItem(event: Event)

    fun getAllBookMarkedItems(): Flow<List<Event>>

    suspend fun removeBookMarkedEvent(id: String)

}