package com.workmate.attendace.usecase.local

import com.workmate.attendace.model.User
import io.reactivex.Single

interface UserLocalLoader {

    fun current(): Single<User>
}