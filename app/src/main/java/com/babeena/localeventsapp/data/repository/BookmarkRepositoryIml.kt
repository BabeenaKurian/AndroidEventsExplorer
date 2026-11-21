package com.babeena.localeventsapp.data.repository

import android.R.attr.description
import com.babeena.localeventsapp.data.local.BookMarkDao
import com.babeena.localeventsapp.data.local.BookMarkEntity
import com.babeena.localeventsapp.domain.model.Event
import com.babeena.localeventsapp.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryIml @Inject constructor(private val bookMarkDao: BookMarkDao) :
    BookMarkRepository {


    override suspend fun addBookMarkedItem(event: Event) {
        bookMarkDao.insert(event.toEntity())
    }

    override fun getAllBookMarkedItems(): Flow<List<Event>> {
        return bookMarkDao.getAllBookMarkedItems().map { entities ->
            entities.map { it.toEvent() }
        }
    }

    override suspend fun removeBookMarkedEvent(id: String) {
        bookMarkDao.removeBookmark(id)
    }

    private fun Event.toEntity(): BookMarkEntity {
        return BookMarkEntity(
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

    private fun BookMarkEntity.toEvent(): Event {
        return Event(
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
}