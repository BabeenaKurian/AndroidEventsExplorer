package com.babeena.localeventsapp.data.remote

import com.babeena.localeventsapp.domain.model.Event
import retrofit2.http.GET

interface EventApiService {

    @GET("/events")
    suspend fun getAllEvents(): List<Event>
}