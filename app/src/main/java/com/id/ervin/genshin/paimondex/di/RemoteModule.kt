package com.id.ervin.genshin.paimondex.di

import com.id.ervin.genshin.paimondex.network.GenshinApiService
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.genshin.dev"

val remoteModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(androidContext()))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    single(named("moshi")) {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single(named("genshin")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get(named("moshi"))))
            .build()
    }

    single<GenshinApiService>(named("genshinApiService")) {
        val retrofit: Retrofit = get(named("genshin"))
        retrofit.create(GenshinApiService::class.java)
    }
}

