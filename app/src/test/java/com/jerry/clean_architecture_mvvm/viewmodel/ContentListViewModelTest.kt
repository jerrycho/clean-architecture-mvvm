package com.jerry.clean_architecture_mvvm.viewmodel

import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import com.jerry.clean_architecture_mvvm.presentation.content.ContentListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContentListViewModelTest {

    private lateinit var viewModel: ContentListViewModel
    private lateinit var getContentUseCase: GetContentUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        getContentUseCase = Mockito.mock(GetContentUseCase::class.java)
        viewModel = ContentListViewModel(getContentUseCase, testDispatcher)
    }

    @Test
    fun `test content view model ui state expected state`() = runTest {
        // Prepare the test data
        given(getContentUseCase()).willReturn(getContentListResponse())

        val event = mutableListOf<ViewState<ContentListResponse>>()
        viewModel.state.onEach {
            event.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )
        viewModel.getContent()

        advanceUntilIdle()

        Assert.assertEquals(3, event.size)

        Assert.assertEquals(ViewState.Initial, event[0])
        Assert.assertEquals(ViewState.Loading, event[1])

        Assert.assertEquals( 0, (event[2] as ViewState.Success<ContentListResponse>).data.items!!.size )
    }

    @Test
    fun `test content view model ui state expected state with error flow`() = runTest {
        // Prepare the test data
        given(getContentUseCase()).willAnswer {
            throw SocketTimeoutException()
        }

        val event = mutableListOf<ViewState<ContentListResponse>>()
        viewModel.state.onEach {
            event.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )
        viewModel.getContent()

        advanceUntilIdle()

        Assert.assertEquals(3, event.size)

        Assert.assertEquals(ViewState.Initial, event[0])
        Assert.assertEquals(ViewState.Loading, event[1])

        Assert.assertEquals( SocketTimeoutException::class.java.simpleName, (event[2] as ViewState.Failure).t.javaClass.simpleName )
    }

    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }

}