package com.babeena.localeventsapp.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun eventScreen(viewModel: EventViewModel){
    val eventUiState by viewModel.eventUiState.collectAsStateWithLifecycle()
}