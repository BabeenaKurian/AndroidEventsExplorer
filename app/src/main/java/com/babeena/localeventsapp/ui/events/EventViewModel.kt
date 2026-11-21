package com.babeena.localeventsapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsapp.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventUiState  = MutableStateFlow(EventUiState())

    var eventUiState: StateFlow<EventUiState> = _eventUiState

    fun getAllEvents(){
     viewModelScope.launch {
         _eventUiState.value = _eventUiState.value.copy(isLoading = true)
         try {
             val events = eventRepository.getAllEvents()
             _eventUiState.value = _eventUiState.value.copy(isLoading = false, data = events)
         }catch (e: Exception){
             _eventUiState.value = _eventUiState.value.copy(isLoading = false, errorMessage = e.message?:"Unable to load Event List...")
         }
     }
    }

    init {
        getAllEvents()
    }
}