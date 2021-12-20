package com.cherish.homeprojectapp.data.repository

import app.cash.turbine.test
import com.cherish.homeprojectapp.data.model.response.GetOrganizationResponse
import com.cherish.homeprojectapp.data.remote.ApiService
import com.cherish.homeprojectapp.data.remote.ResponseManager
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
class DataRepositoryTest{
    private lateinit var repository: DataRepository
    private val testDispatcher = TestCoroutineDispatcher()

    private val apiService = mock<ApiService>()


    @Before
    fun setUp(){
        repository = DataRepository(apiService, testDispatcher)

    }

    @Test
    fun `test that get organization emits data correctly`() = runBlocking {
        val list = mutableListOf<GetOrganizationResponse>()
        val response = GetOrganizationResponse("https://avatars.githubusercontent.com/u/3286?v=4", "description message","https://events.com","https://api.github.com/orgs/hooks/hooks", 9,"https://api.github.com/orgs/issues/issues","login_name", "https://api.github.com/orgs/members/member", "JHSHSB3488R==", "https://api.github.com/orgs/public/members","https://api.github.com/orgs/repo/repo","https://api.github.com/orgs/url/url")
        list.add(response)

        // Mock API Service
        apiService.stub {
            onBlocking { getOrganizations() } doReturn list
        }
        //Test and Verify
        repository.getOrganizations().test {
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true)) // emitting on start
            assertThat(awaitItem()).isEqualTo(ResponseManager.Success(list))    // emitting on success
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) // emitting on completion
            cancelAndConsumeRemainingEvents()
        }
    }

}