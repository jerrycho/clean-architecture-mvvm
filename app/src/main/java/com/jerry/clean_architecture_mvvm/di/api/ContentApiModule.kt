package com.jerry.clean_architecture_mvvm.di.api

import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContentApiModule {

    @Singleton
    @Provides
    fun provideContentApiService(retrofit: Retrofit): ContentApiService {
        return retrofit.create(ContentApiService::class.java)
    }

}