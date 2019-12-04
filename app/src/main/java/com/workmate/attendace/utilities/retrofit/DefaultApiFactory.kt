package com.workmate.attendace.utilities.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.workmate.attendace.BuildConfig
import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultApiFactory

    @Inject
    internal constructor(private val cache: Cache): ApiFactory {
    override fun <T> create(apiClass: Class<T>): Single<T> {
        return retrofit()
            .map { retrofit -> retrofit.create(apiClass) }
    }

    override fun <T> createWithApiKeys(apiClass: Class<T>): Single<T> {
        return retrofitWithApiKey()
            .map { retrofit -> retrofit.create(apiClass) }
    }


    private fun addInterceptorForApiKey(
        builder: OkHttpClient.Builder): Single<OkHttpClient.Builder> {
        return Single.just(
            builder.addInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header(
                        "Authorization",
                        "token " + cache.getString(SharedPrefKeys.API_KEY)
                    )
                    .build()
                it.proceed(request)
            })
    }

    private fun addLoggingInterceptor(
        builder: OkHttpClient.Builder): Single<OkHttpClient.Builder> {
        return Single.fromCallable { builder }
            .map {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                it.addInterceptor(loggingInterceptor)
                it.readTimeout(60, TimeUnit.SECONDS)
                it
            }
    }

    private fun client(): Single<OkHttpClient> {
        return Single.fromCallable { OkHttpClient.Builder() }
            .flatMap { addLoggingInterceptor(it) }
            .map { it.build() }

    }


    private fun clientWitApiKey(): Single<OkHttpClient> {
        return Single.fromCallable { OkHttpClient.Builder() }
            .flatMap { addLoggingInterceptor(it) }
            .flatMap { addInterceptorForApiKey(it) }
            .map { it.build() }
    }

    private fun intoRetrofit(client: OkHttpClient): Single<Retrofit> {
        return Single.fromCallable {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    private fun retrofit(): Single<Retrofit> {
        return client().flatMap { intoRetrofit(it) }
    }

    private fun retrofitWithApiKey(): Single<Retrofit> {
        return clientWitApiKey().flatMap { intoRetrofit(it) }
    }
}