package com.workmate.attendace.model

data class Location(
    val id: String,
    val name: String,
    val country: Country,
    val is_primary: Boolean,
    val contact_name: String,
    val contact_phone: String?
)