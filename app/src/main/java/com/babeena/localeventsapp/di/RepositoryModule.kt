package com.babeena.localeventsapp.di

import com.babeena.localeventsapp.data.repository.BookmarkRepositoryIml
import com.babeena.localeventsapp.data.repository.EventRepositoryImpl
import com.babeena.localeventsapp.domain.repository.BookMarkRepository
import com.babeena.localeventsapp.domain.repository.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        implementation: EventRepositoryImpl): EventRepository

    @Binds
    @Singleton
    abstract fun bindBookMarkRepository(
        implementation: BookmarkRepositoryIml
    ): BookMarkRepository
}