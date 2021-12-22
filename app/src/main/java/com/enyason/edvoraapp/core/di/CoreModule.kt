package com.enyason.edvoraapp.core.di

import com.enyason.edvoraapp.core.api.EdvoraApi
import com.enyason.edvoraapp.core.domain.usecase.GetProducts
import com.enyason.edvoraapp.core.domain.usecase.GetProductsImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://assessment-edvora.herokuapp.com"

val coreModule = module {

    factory<GetProducts> { GetProductsImpl(get()) }

    single<EdvoraApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EdvoraApi::class.java)
    }

    factory { OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build() }

    factory { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

}