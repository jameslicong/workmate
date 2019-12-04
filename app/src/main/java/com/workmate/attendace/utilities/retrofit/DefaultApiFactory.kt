package com.workmate.attendace.utilities.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.workmate.attendace.BuildConfig
import com.workmate.attendace.utilities.Constants.SharedPrefKeys
import com.workmate.attendace.utilities.preference.Cache
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private fun client(): Single<OkHttpClient> {
        return Single.fromCallable { OkHttpClient.Builder() }
            .map { it.build() }

    }

    private fun clientWitApiKey(): Single<OkHttpClient> {
        return Single.fromCallable { OkHttpClient.Builder() }
            .flatMap { addInterceptorForApiKey(it) }
            .map { it.build() }
    }

    private fun intoRetrofit(client: OkHttpClient): Single<Retrofit> {
        return Single.fromCallable {
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