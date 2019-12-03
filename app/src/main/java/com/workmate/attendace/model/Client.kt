package com.workmate.attendace.model

data class Client(
    private val id: String,
    private val name: String,
    private val status: String,
    private val logo: String,
    private val tier: String,
    private val website: String,
    private val description: String
)