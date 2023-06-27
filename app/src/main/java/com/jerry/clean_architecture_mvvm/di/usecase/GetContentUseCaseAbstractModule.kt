package com.jerry.clean_architecture_mvvm.di.usecase

import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class GetContentUseCaseAbstractModule {

    @Binds
    @Singleton
    abstract fun bindGetContentUseCase(getContentUseCaseImpl: GetContentUseCaseImpl): GetContentUseCase


}