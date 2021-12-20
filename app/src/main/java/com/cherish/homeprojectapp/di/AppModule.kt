package com.cherish.homeprojectapp.di

import com.cherish.homeprojectapp.BuildConfig
import com.cherish.homeprojectapp.data.remote.ApiService
import com.cherish.homeprojectapp.utils.AppConstant.REQUEST_TIMEOUT_DURATION
import com.cherish.homeprojectapp.utils.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val builder = OkHttpClient.Builder()
            .callTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT_DURATION, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}