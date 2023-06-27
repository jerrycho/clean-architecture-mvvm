package com.jerry.clean_architecture_mvvm.domain.usecase.impl


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.others.MyResult
import javax.inject.Inject


class GetContentUseCaseImpl @Inject constructor(
    private val contentRepository: ContentRepository
): GetContentUseCase {

    override operator fun invoke(): Flow<MyResult<ContentListResponse>> = flow {
        try {
            emit(MyResult.Loading<ContentListResponse>(true))
            val contents = contentRepository.getContent()
            emit(MyResult.Success<ContentListResponse>(contents))
            emit(MyResult.Loading<ContentListResponse>(false))
        } catch(e: Exception) {
            emit(MyResult.Loading<ContentListResponse>(false))
            emit(MyResult.Error<ContentListResponse>(e))
        }
    }
}