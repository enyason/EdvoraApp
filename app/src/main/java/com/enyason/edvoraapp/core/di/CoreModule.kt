package com.enyason.edvoraapp.core.di

import com.enyason.edvoraapp.core.api.EdvoraApi
import com.enyason.edvoraapp.core.domain.usecase.GetProductsImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://assessment-edvora.herokuapp.com"

val coreModule = module {

    factory { GetProductsImpl(get()) }

    single<EdvoraApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EdvoraApi::class.java)
    }

}