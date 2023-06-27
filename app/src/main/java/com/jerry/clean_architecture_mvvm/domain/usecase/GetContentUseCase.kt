package com.jerry.clean_architecture_mvvm.domain.usecase

import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.others.MyResult
import kotlinx.coroutines.flow.Flow

interface GetContentUseCase {
    operator fun invoke(): Flow<MyResult<ContentListResponse>>
}