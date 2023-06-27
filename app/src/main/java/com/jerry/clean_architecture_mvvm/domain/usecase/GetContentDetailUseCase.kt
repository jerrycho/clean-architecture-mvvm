package com.jerry.clean_architecture_mvvm.domain.usecase

import com.jerry.clean_architecture_mvvm.domain.entities.ContentDetailResponse
import com.jerry.clean_architecture_mvvm.others.MyResult
import kotlinx.coroutines.flow.Flow

interface GetContentDetailUseCase {
    operator fun invoke(id: Int): Flow<MyResult<ContentDetailResponse>>
}