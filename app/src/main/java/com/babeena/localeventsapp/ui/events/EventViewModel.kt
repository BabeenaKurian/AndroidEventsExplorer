package com.babeena.localeventsapp.ui.events

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventViewModel : ViewModel() {
    private val _eventUiState  = MutableStateFlow(EventUiState())

    var eventUiState: StateFlow<EventUiState> = _eventUiState

    fun getAllEvents(){

    }

    init {
        getAllEvents()
    }
}