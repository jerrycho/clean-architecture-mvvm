package com.jerry.clean_architecture_mvvm.domain.usecase.impl


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.jerry.clean_architecture_mvvm.domain.entities.ContentDetailResponse
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentDetailUseCase
import com.jerry.clean_architecture_mvvm.others.MyResult
import javax.inject.Inject

open class GetContentDetailUseCaseImpl @Inject constructor(
    private val contentRepository: ContentRepository
): GetContentDetailUseCase {

    override operator fun invoke(id: Int): Flow<MyResult<ContentDetailResponse>> = flow {
        try {
            emit(MyResult.Loading<ContentDetailResponse>(true))
            val content = contentRepository.getContentById(id)
            emit(MyResult.Success<ContentDetailResponse>(content))
            emit(MyResult.Loading<ContentDetailResponse>(false))
        } catch(e: Exception) {
            emit(MyResult.Loading<ContentDetailResponse>(false))
            emit(MyResult.Error<ContentDetailResponse>(e))
        }
    }
}