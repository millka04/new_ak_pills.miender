package com.example.real_ak_pilsmineder.domain.repository


import com.example.real_ak_pilsmineder.domain.model.Intake
import kotlinx.coroutines.flow.Flow

interface IntakeRepository {
    fun getAllIntakes(): Flow<List<Intake>>
    suspend fun addIntake(intake: Intake): Long

}