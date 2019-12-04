package com.workmate.attendace.utilities.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.workmate.attendace.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class RetrofitModule {

    @Binds
    internal abstract fun bindApiFactory(
        apiFactory: DefaultApiFactory
    ): ApiFactory
}