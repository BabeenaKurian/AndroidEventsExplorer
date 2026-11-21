package com.babeena.localeventsapp.ui.details

import com.babeena.localeventsapp.domain.model.Event

data class DetailsUiState(
    val isLoading: Boolean = false,
    val event: Event? = null,
    val errorMessage: String? = null
)
