package com.jerry.clean_architecture_mvvm.usecase.di


import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.di.api.ContentApiModule
import com.jerry.clean_architecture_mvvm.di.usecase.GetContentUseCaseAbstractModule
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.Mockito
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ContentApiModule::class]
)
class FakeGetContentUseCaseModule {

    @Singleton
    @Provides
    fun provideContentApiService(): ContentApiService = Mockito.mock(ContentApiService::class.java)

}