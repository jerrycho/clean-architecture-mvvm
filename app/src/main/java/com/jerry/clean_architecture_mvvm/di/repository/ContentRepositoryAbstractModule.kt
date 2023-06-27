package com.jerry.clean_architecture_mvvm.di.repository

import com.jerry.clean_architecture_mvvm.data.respository.ContentRepositoryImpl
import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentRepositoryAbstractModule {

    @Provides
    @Singleton
    fun provideContentRepository(contentApiService: ContentApiService): ContentRepository {
        return ContentRepositoryImpl(contentApiService)
    }

}