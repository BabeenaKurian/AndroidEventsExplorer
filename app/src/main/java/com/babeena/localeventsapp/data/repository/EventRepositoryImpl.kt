package com.babeena.localeventsapp.data.repository

import com.babeena.localeventsapp.data.local.EventDao
import com.babeena.localeventsapp.data.local.EventEntity
import com.babeena.localeventsapp.data.remote.EventApiService
import com.babeena.localeventsapp.domain.model.Event
import com.babeena.localeventsapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApiService: EventApiService,
    private val cacheTimestampStore: CacheTimestampStore,
    val eventDao: EventDao,
) : EventRepository {

    override suspend fun getAllEvents(): List<Event> {
        val cacheAge = System.currentTimeMillis() -
                cacheTimestampStore.getLastFetchTime()

        val cacheIsFresh = cacheTimestampStore.getLastFetchTime() > 0L &&
                cacheAge < TimeUnit.MINUTES.toMillis(5)

        if (cacheIsFresh) {
            val cachedEvents = eventDao.getAllEvents().first()
            if (cachedEvents.isNotEmpty()) {
                return cachedEvents.map { it.toEvent() }
            }
        }
        return try {
            val events = eventApiService.getAllEvents()
            eventDao.deleteAllEvents()
            eventDao.insertEvents(events.map { it.toEntity() })
            cacheTimestampStore.saveLastFetchTime(System.currentTimeMillis())
            events
        } catch (e: Exception) {
            val cachedEvents = eventDao.getAllEvents().first()
            if (cachedEvents.isNotEmpty()) {
                cachedEvents.map { it.toEvent() }
            } else {
                throw e
            }
        }
    }

    override suspend fun getEventDetails(id: String): Event {
        val event = eventDao.getEventById(id)
        if (event != null) {
            return event.toEvent()
        } else {
            return eventApiService.getEventDetails(id)
        }
    }


    private fun Event.toEntity() = EventEntity(
        id = id,
        title = title,
        location = location,
        dateTime = dateTime,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        description = description
    )

    private fun EventEntity.toEvent() = Event(
        id = id,
        title = title,
        location = location,
        dateTime = dateTime,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        description = description
    )
}