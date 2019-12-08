package com.workmate.attendance.model

data class Address(
    val id: String,
    val country: Country,
    val street_1: String,
    val street_2: String,
    val zip: String
)