package com.cherish.homeprojectapp.data.repository

import com.cherish.homeprojectapp.data.remote.ApiService
import com.cherish.homeprojectapp.data.remote.ResponseManager
import com.cherish.homeprojectapp.utils.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DataRepository@Inject constructor(private val apiService: ApiService, @IODispatcher private val ioDispatcher: CoroutineDispatcher) {

    fun getOrganizations() = flow {
        val response = apiService.getOrganizations()
        emit(ResponseManager.Success(response))
        emit(ResponseManager.Loading(false))
    }.flowOn(ioDispatcher)
        .catch { emit(ResponseManager.Failure(it)) }
        .onStart {
            emit(ResponseManager.Loading(true))
        }
        .onCompletion {
            emit(ResponseManager.Loading(false))
        }
}