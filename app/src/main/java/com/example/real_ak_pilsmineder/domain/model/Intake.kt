package com.example.real_ak_pilsmineder.domain.model

data class Intake(
    val id: Long = 0,
    val preparatId: Long,
    val duringDay: Int,
    val often: String,
    val weekday: String
)