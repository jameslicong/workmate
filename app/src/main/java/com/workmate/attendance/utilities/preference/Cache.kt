package com.workmate.attendance.utilities.preference

interface Cache {

    fun getLong(key: String): Long

    fun getString(key: String): String?

    fun setLong(key: String, value: Long)

    fun setString(key: String, value: String)

    fun remove(key: String)

}