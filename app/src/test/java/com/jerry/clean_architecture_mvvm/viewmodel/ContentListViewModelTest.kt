package com.jerry.clean_architecture_mvvm.viewmodel

import app.cash.turbine.test
import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import com.jerry.clean_architecture_mvvm.presentation.content.ContentListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.given
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [25], application = HiltTestApplication::class)
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
class ContentListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var contentApiService: ContentApiService

    @Inject
    lateinit var contentRepository: ContentRepository

    @Inject
    lateinit var getContentUseCaseImpl: GetContentUseCaseImpl

    lateinit var viewModel: ContentListViewModel

    @Before
    fun setup() {
        // Initialize Hilt
        hiltRule.inject()
        Dispatchers.setMain(dispatcher)
        viewModel = ContentListViewModel(getContentUseCaseImpl)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    private val testScope = TestCoroutineScope(dispatcher)

    @Test
    fun `get content usecase success`() = runTest {

        val job = launch {
            // Force my flow to update via collect invocation
            val list = viewModel.state.toList()

            println("list::")

            viewModel.getContent()

        }

        // immediately cancel job
        job.cancel()

        //action


    }

    @Test
    fun `get content usecase success_2`() = runBlockingTest {
        launch {
            //assign
            given(contentApiService.getContentList()).willReturn(getContentListResponse())

            val sss = viewModel.state.toList()
            println(sss.size)
//            viewModel.state.toList() test {
//                print (awaitItem())
//                Assert.assertEquals(ViewState.Initial, awaitItem())
//                Assert.assertEquals(ViewState.Loading, awaitItem())
//            }

            //action
            viewModel.getContent()
        }
    }

    @Test
    fun `get content usecase success2`() {
        runTest {
            launch {
                //assign
                given(contentApiService.getContentList()).willReturn(getContentListResponse())

                viewModel.state.test {
                    Assert.assertEquals(ViewState.Initial, awaitItem())
                    Assert.assertEquals(ViewState.Loading, awaitItem())


                    println (awaitItem())


//                Assert.assertEquals(true, awaitItem().isLoading == null )
//
//                Assert.assertEquals(true,awaitItem().isLoading)
//
//                Assert.assertEquals(getContentListResponse().items!!.size, awaitItem().data?.items?.size)
//                Assert.assertEquals(false,awaitItem().isLoading)

                    awaitComplete()
                }

                //action
                viewModel.getContent()
            }

        }
//        runBlockingTest  {
//
//                given(contentApiService.getContentList()).willReturn(getContentListResponse())
//
//                //fire
//                viewModel.getContent()
//                val list = viewModel.state.toList()
//
////                Assert.assertEquals(3, list.size)
////
////                Assert.assertEquals(true, list[0].isLoading)
////                Assert.assertEquals(
////                    getContentListResponse().items!!.size,
////                    list[1].data?.items?.size
////                )
////                Assert.assertEquals(false, list[2].isLoading)
//            }

    }

    //"{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }
}