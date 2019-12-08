package com.workmate.attendance.usecase

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.workmate.attendance.utilities.exceptions.NoInternetNetworkException
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class DefaultInternet

    @Inject
    internal constructor() : Internet {

    override fun isConnected(): Completable {
        return Completable
            .defer {
                Retrofit.Builder()
                    .baseUrl("https://www.google.com")
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(Google::class.java)
                    .homepage()
            }
            .onErrorResumeNext { Completable.error(NoInternetNetworkException()) }
            .subscribeOn(Schedulers.io())
    }

    interface Google {

        @GET("")
        fun homepage(@Url url: String = ""): Completable
    }
}