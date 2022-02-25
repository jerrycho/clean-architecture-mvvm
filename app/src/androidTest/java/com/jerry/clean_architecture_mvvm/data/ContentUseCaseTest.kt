package com.jerry.clean_architecture_mvvm.data


import com.jerry.clean_architecture_mvvm.data.respository.ContentRepositoryImpl
import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.others.MyResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ContentUseCaseTest {
    private val dispatcher = UnconfinedTestDispatcher()

    private val mockContentApiService : ContentApiService = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test test`() {
        runTest {
            whenever(mockContentApiService.getContentList()).thenReturn(getContentListResponse())
            val contentRepository = ContentRepositoryImpl(mockContentApiService)

            val list = contentRepository.getContent()

            Assert.assertEquals(list.items!!.size, getContentListResponse().items!!.size)
        }

    }

    @Test
    fun combineUnendingFlows() = runBlockingTest {
        val job = Job()
        val childScope = CoroutineScope(this.coroutineContext + job)

        val contentRepository = ContentRepositoryImpl(mockContentApiService)
        val mockGetContentUseCase = GetContentUseCase(contentRepository)

        whenever(mockContentApiService.getContentList()).thenReturn(getContentListResponse())

        val source3 = mockGetContentUseCase().onEach {result ->
            when (result) {
                is MyResult.Loading -> {
                    //_state.value = ViewStated(isLoading = result.isLoading )
                    System.out.println("hihi")
                }
                is MyResult.Success -> {
                    //_state.value = ViewStated<ContentListResponse>( data = result.data )
                    System.out.println("hihi2")
                }
                is MyResult.Error -> {
                    //
                    System.out.println("hihi3")
                }
            }

        }.launchIn(childScope)

        advanceUntilIdle()
        source3.cancel()
    }

    //"{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }
}