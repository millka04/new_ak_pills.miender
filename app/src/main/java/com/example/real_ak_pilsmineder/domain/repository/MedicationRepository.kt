package com.example.real_ak_pilsmineder.domain.repository


import com.example.real_ak_pilsmineder.domain.model.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    fun getAllMedications(): Flow<List<Medication>>
    suspend fun addMedication(medication: Medication)
    suspend fun updateMedication(medication: Medication)

    suspend fun deleteMedication(medication: Medication)
}