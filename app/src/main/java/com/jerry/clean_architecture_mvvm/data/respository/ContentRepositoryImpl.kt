package com.jerry.clean_architecture_mvvm.data.respository



import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.domain.entities.ContentDetailResponse
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository

class ContentRepositoryImpl (
    private val contentApiService : ContentApiService
) : ContentRepository {

    override suspend fun getContent(): ContentListResponse {
        return contentApiService.getContentList()
    }

    override suspend fun getContentById(id: Int): ContentDetailResponse {
        return contentApiService.getContentById(id)
    }

}
