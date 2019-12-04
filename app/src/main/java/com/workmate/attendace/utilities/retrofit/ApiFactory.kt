package com.workmate.attendace.utilities.retrofit

import io.reactivex.Single

interface ApiFactory {

    fun <T> create(apiClass: Class<T>): Single<T>

    fun <T> createWithApiKeys(apiClass: Class<T>): Single<T>
}