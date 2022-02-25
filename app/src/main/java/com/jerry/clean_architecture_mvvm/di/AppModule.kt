package com.jerry.clean_architecture_mvvm.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.jerry.clean_architecture_mvvm.data.respository.ContentRepositoryImpl
import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.others.TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.jerry.clean_architecture_mvvm.BuildConfig;
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContentRepository(contentApiService: ContentApiService): ContentRepository {
        return ContentRepositoryImpl(contentApiService)
    }

    @Singleton
    @Provides
    fun provideContentApiService(retrofit: Retrofit): ContentApiService {
        return retrofit.create(ContentApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {

        //basic setting
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val fileLogger = HttpLoggingInterceptor.Logger() {
            val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val currentTime = timeFormat.format(Date())
            Log.d("NetworkModule", "$currentTime - $it")

        }
        val httpLoggingInterceptor = HttpLoggingInterceptor(fileLogger)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }



    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BuildConfig.BASE_URL).build()
    }


}