package com.babeena.localeventsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsapp.domain.model.Event
import com.babeena.localeventsapp.domain.repository.BookMarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val bookmarkRepository: BookMarkRepository
) : ViewModel() {

    val bookmarkedEvents: StateFlow<List<Event>> =
        bookmarkRepository.getAllBookMarkedItems().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun removeBookmark(eventId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookMarkedEvent(eventId)
        }
    }

}