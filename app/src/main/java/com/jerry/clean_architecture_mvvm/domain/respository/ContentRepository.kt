package com.jerry.clean_architecture_mvvm.domain.respository

import com.jerry.clean_architecture_mvvm.domain.entities.ContentDetailResponse
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse


interface ContentRepository {
    suspend fun getContent(): ContentListResponse
    suspend fun getContentById(id: Int): ContentDetailResponse
}