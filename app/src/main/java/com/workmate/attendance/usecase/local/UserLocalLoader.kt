package com.workmate.attendance.usecase.local

import com.workmate.attendance.model.User
import io.reactivex.Single

interface UserLocalLoader {

    fun current(): Single<User>
}