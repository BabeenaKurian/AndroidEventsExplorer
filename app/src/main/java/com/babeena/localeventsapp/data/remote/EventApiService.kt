package com.babeena.localeventsapp.data.remote

import com.babeena.localeventsapp.domain.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {

    @GET("/events")
    suspend fun getAllEvents(): List<Event>

    @GET("/events/{id}")
    suspend fun getEventDetails(@Path("id")id: String): Event

}