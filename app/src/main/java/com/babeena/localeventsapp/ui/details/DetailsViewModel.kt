package com.babeena.localeventsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val eventRepository: EventRepository) :
    ViewModel() {
    private val _detailsUiState = MutableStateFlow(DetailsUiState())

    val detailsUiState: StateFlow<DetailsUiState> = _detailsUiState

    fun getEventDetails(eventId: String) {
        viewModelScope.launch {
            _detailsUiState.value = _detailsUiState.value.copy(isLoading = true)
            try {
                val result = eventRepository.getEventDetails(eventId)
                _detailsUiState.value =
                    _detailsUiState.value.copy(isLoading = false, event = result)

            } catch (e: Exception) {
                _detailsUiState.value = _detailsUiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unable to load Data..."
                )
            }
        }
    }
}