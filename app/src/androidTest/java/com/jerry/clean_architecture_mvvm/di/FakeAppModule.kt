package com.jerry.clean_architecture_mvvm.di

import com.google.gson.GsonBuilder
import com.jerry.clean_architecture_mvvm.BuildConfig
import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.di.api.ContentApiModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


const val TIME_OUT : Long = 10


@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ContentApiModule::class, AppModule::class]
)
@Module
class FakeAppModule {

    @Singleton
    @Provides
    fun provideContentApiService(retrofit: Retrofit): ContentApiService {
        return retrofit.create(ContentApiService::class.java)
    }


    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)


        builder.addInterceptor(interceptor)


        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://127.0.0.1:8080")
            .build()
    }
}