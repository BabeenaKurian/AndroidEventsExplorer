package com.babeena.localeventsapp.di

import com.babeena.localeventsapp.data.repository.EventRepositoryImpl
import com.babeena.localeventsapp.domain.repository.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEventRepository(
        implementation: EventRepositoryImpl): EventRepository
}