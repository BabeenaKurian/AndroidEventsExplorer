package com.babeena.localeventsapp.data.repository

import com.babeena.localeventsapp.data.remote.EventApiService
import com.babeena.localeventsapp.domain.model.Event
import com.babeena.localeventsapp.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val eventApiService: EventApiService): EventRepository {
    override suspend fun getAllEvents(): List<Event> {
        return eventApiService.getAllEvents()
    }

    override suspend fun getEventDetails(id: String): Event {
            return eventApiService.getEventDetails(id)
    }
}