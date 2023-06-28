package com.jerry.clean_architecture_mvvm.usecase



import com.jerry.clean_architecture_mvvm.data.source.network.ContentApiService
import com.jerry.clean_architecture_mvvm.di.usecase.GetContentUseCaseAbstractModule
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.respository.ContentRepository
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import dagger.Provides
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import javax.inject.Inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import app.cash.turbine.*
import com.jerry.clean_architecture_mvvm.others.MyResult
import kotlinx.coroutines.flow.toList
import org.mockito.kotlin.given
import java.net.SocketTimeoutException


@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [25], application = HiltTestApplication::class)
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
class ContentUseCaseImplTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var contentApiService: ContentApiService

    @Inject
    lateinit var contentRepository: ContentRepository

    @Inject
    lateinit var getContentUseCaseImpl: GetContentUseCaseImpl

    @Before
    fun setup() {
        // Initialize Hilt
        hiltRule.inject()
    }

    @Test
    fun `get content usecase success`() {
        runBlockingTest  {

            given(contentApiService.getContentList()).willReturn(getContentListResponse())

            val ssss = getContentUseCaseImpl()
            val list = ssss.toList()

            Assert.assertEquals(3, list.size)

            Assert.assertEquals(true, list[0].isLoading)
            Assert.assertEquals(getContentListResponse().items!!.size, list[1].data?.items?.size)
            Assert.assertEquals(false, list[2].isLoading)

        }
    }

    @Test
    fun `get content usecase exception`() {
        runBlockingTest  {
            given(
                contentApiService.getContentList()
            ).willAnswer{
                throw Exception()
            }

            val ssss = getContentUseCaseImpl()
            val list = ssss.toList()

            Assert.assertEquals(3, list.size)

            Assert.assertEquals(true, list[0].isLoading)
            Assert.assertEquals(false, list[1].isLoading)
            Assert.assertEquals(Exception::class.java, list[2].t?.javaClass)

        }
    }

    //"{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }
}

