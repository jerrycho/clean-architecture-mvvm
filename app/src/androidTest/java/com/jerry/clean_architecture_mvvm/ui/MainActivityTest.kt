package com.jerry.clean_architecture_mvvm.ui


import android.os.SystemClock

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule

import com.google.gson.Gson
import com.jerry.clean_architecture_mvvm.MainActivity
import com.jerry.clean_architecture_mvvm.domain.entities.Content
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var mockWebServer: MockWebServer


    @Rule
    @JvmField
    var mMainActivityResult = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_all_success() {
        mockNetworkResponse("list", HttpURLConnection.HTTP_OK)


        mMainActivityResult.launchActivity(null)
        //waiting loading
        SystemClock.sleep(2000)

        //check contain 500
//        Espresso.onView(ViewMatchers.withId(R.id.message)).check(
//            ViewAssertions.matches(ViewMatchers.withSubstring("500"))
//        )

        SystemClock.sleep(100)
    }


    private fun mockNetworkResponse(path: String, responseCode: Int) = mockWebServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(
                Gson().toJson(
                    getContentListResponse()
                )
            )
    )

    //"{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = listOf(
                Content(
                    id = 11,
                    title = "Title 11",
                    subtitle = "Subtitle 11",
                    date = "date 11"
                ),
                Content(
                    id = 12,
                    title = "Title 12",
                    subtitle = "Subtitle 12",
                    date = "date 12"
                )
            )
        )
    }
}