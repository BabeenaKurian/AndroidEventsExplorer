package com.babeena.localeventsapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsapp.domain.model.Event
import com.babeena.localeventsapp.domain.repository.BookMarkRepository
import com.babeena.localeventsapp.domain.repository.EventRepository
import com.babeena.localeventsapp.utils.LocationHelper
import com.babeena.localeventsapp.utils.UserLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val locationHelper: LocationHelper,
    private val bookmarkRepository: BookMarkRepository
) : ViewModel() {
    private val _eventUiState = MutableStateFlow(EventUiState())

    var eventUiState: StateFlow<EventUiState> = _eventUiState

    private val _userLocation =
        MutableStateFlow<UserLocation?>(null)

    val userLocation = _userLocation.asStateFlow()

    private val _bookmarkedEventIds = MutableStateFlow<Set<String>>(emptySet())

    val bookmarkedEventIds: StateFlow<Set<String>> = _bookmarkedEventIds

    fun fetchUserLocation() {
        viewModelScope.launch {
            _userLocation.value =
                locationHelper.getCurrentLocation()
        }
    }
    fun getAllEvents() {
        viewModelScope.launch {
            _eventUiState.value = _eventUiState.value.copy(isLoading = true)
            try {
                val events = eventRepository.getAllEvents()
                _eventUiState.value = _eventUiState.value.copy(isLoading = false, data = events)
            } catch (e: Exception) {
                _eventUiState.value = _eventUiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unable to load Event List..."
                )
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkRepository.getAllBookMarkedItems().collect{
                    bookmark ->
                _bookmarkedEventIds.value =bookmark.map { it.id }.toSet()
            }
        }
    }

    fun toggleBookmark(event: Event) {
        if (event.id in _bookmarkedEventIds.value) {
            removeBookmark(event.id)
        } else {
            addBookmark(event)
        }
    }

    fun addBookmark(event: Event) {
        viewModelScope.launch {
            bookmarkRepository.addBookMarkedItem(event)
        }
    }

    fun removeBookmark(eventId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookMarkedEvent(eventId)
        }
    }
    init {
        getAllEvents()
        observeBookmarks()
    }
}