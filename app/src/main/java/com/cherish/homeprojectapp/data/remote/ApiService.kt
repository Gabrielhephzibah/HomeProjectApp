package com.cherish.homeprojectapp.data.remote

import com.cherish.homeprojectapp.data.model.response.GetOrganizationResponse
import retrofit2.http.GET

interface ApiService {
    @GET("organizations")
    suspend fun getOrganizations(): List<GetOrganizationResponse>
}