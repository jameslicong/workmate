package com.workmate.attendance.model

import java.io.Serializable

data class JobInformation(
    val id: String,
    val title: String,
    val description: String,
    val wage_amount: String,
    val wage_type: String,
    val client: Client,
    val location: Location,
    val position: JobPosition,
    val manager: Manager
) : Serializable