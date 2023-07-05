package com.jerry.clean_architecture_mvvm.viewmodel

import app.cash.turbine.test

import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import com.jerry.clean_architecture_mvvm.presentation.content.ContentListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.given

@RunWith(JUnit4::class)
class ContentListViewModelTest3 {


    @Mock
    lateinit var mockGetContentUseCaseImpl: GetContentUseCaseImpl

    // 2.
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        // setting up test dispatcher as main dispatcher for coroutines
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        // removing the test dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `Given the sut is initialized, then it waits for event`() {

        // 3.
        val viewModel: ContentListViewModel = ContentListViewModel(mockGetContentUseCaseImpl)

        // 4.
        assertEquals(ViewState.Initial, viewModel.state.value)
    }

    @Test
    fun `Given the ViewModel waits - When the event OnLaunch comes, then execute heavy computation with result`() =
        runTest {
            var viewModel: ContentListViewModel? = null
            given(mockGetContentUseCaseImpl()).willReturn(getContentListResponse())

            viewModel =  ContentListViewModel(mockGetContentUseCaseImpl)

            viewModel.state.test {  }


            viewModel.getContent()
        }

    //"{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }
}