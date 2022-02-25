package com.jerry.clean_architecture_mvvm.data.source.network


import com.jerry.clean_architecture_mvvm.domain.entities.ContentDetailResponse
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ContentApiService {

    @GET("/jerrycho/mvvm/main/json/content_list.json")
    suspend fun getContentList(): ContentListResponse

    @GET("/jerrycho/mvvm/main/json/detail_{contentId}.json")
    suspend fun getContentById(@Path("contentId") contentId: Int): ContentDetailResponse

}