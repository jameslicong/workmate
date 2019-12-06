package com.workmate.attendance.model

data class Location(
    val id: String,
    val name: String,
    val address: Address,
    val is_primary: Boolean,
    val contact_name: String,
    val contact_phone: String?
)