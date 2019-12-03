package com.workmate.attendace.model

data class JobInformation(
    private val id: String,
    private val title: String,
    private val description: String,
    private val wage_amount: String,
    private val wage_type: String,
    private val client: Client,
    private val location: Location,
    private val position: JobPosition,
    private val manager: Manager
)