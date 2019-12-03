package com.workmate.attendace.model

data class Location(
    private val id: String,
    private val name: String,
    private val country: Country,
    private val is_primary: Boolean,
    private val contact_name: String,
    private val contact_phone: String?
)