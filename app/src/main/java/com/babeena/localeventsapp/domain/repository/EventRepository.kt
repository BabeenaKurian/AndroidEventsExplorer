package com.babeena.localeventsapp.domain.repository

import com.babeena.localeventsapp.domain.model.Event

interface EventRepository {

    suspend fun getAllEvents(): List<Event>

    suspend fun getEventDetails(id: String): Event

    suspend fun syncEvents()

}