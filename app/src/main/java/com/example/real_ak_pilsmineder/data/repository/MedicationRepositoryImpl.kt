package com.example.real_ak_pilsmineder.data.repository

import com.example.real_ak_pilsmineder.data.local.dao.MedicationDao
import com.example.real_ak_pilsmineder.data.local.entity.MedicationEntity
import com.example.real_ak_pilsmineder.domain.model.Medication
import com.example.real_ak_pilsmineder.domain.repository.MedicationRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationRepositoryImpl(
    private val dao: MedicationDao
) : MedicationRepository {

    override fun getAllMedications(): Flow<List<Medication>> =
        dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addMedication(medication: Medication) {
        dao.insert(medication.toEntity())
    }
}
private fun MedicationEntity.toDomain(): Medication = Medication(
    id = id,
    name = name
)

private fun Medication.toEntity(): MedicationEntity = MedicationEntity(
    id = id,
    name = name
)