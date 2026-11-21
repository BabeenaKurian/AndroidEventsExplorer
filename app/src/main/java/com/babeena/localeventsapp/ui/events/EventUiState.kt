package com.babeena.localeventsapp.ui.events

import com.babeena.localeventsapp.domain.model.Event

data class EventUiState(
    val data: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)
