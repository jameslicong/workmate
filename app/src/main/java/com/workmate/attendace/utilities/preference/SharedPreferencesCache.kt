package com.workmate.attendace.utilities.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesCache

    @Inject
    internal constructor(context: Context) : Cache {

    private var sharedPreferences : SharedPreferences
    init {
        val sharedPreferencesName = context.packageName + "_preferences"
        sharedPreferences = context.getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)
    }

    override fun getLong(key: String): Long? {
        return sharedPreferences.getLong(key, 0)
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    override fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}