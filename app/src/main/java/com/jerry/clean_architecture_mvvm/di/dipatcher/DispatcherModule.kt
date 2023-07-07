package com.jerry.clean_architecture_mvvm.di.dipatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class DispatcherModule {

//    @Provides
//    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    // or

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher  {
        val aaa = Dispatchers.Default
        return aaa
    }
}