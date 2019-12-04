package com.workmate.attendace.model

data class Client(
    val id: String,
    val name: String,
    val status: String,
    val logo: String,
    val tier: String,
    val website: String,
    val description: String
)