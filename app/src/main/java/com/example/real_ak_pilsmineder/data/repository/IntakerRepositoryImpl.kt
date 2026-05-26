package com.example.real_ak_pilsmineder.data.repository

import com.example.real_ak_pilsmineder.data.local.dao.IntakeDao
import com.example.real_ak_pilsmineder.data.local.entity.IntakeEntity
import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.repository.IntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class IntakeRepositoryImpl(
    private val dao: IntakeDao
) : IntakeRepository {

    override fun getAllIntakes(): Flow<List<Intake>> =
        dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addIntake(intake: Intake): Long {
        return dao.insert(intake.toEntity())
    }
}

private fun IntakeEntity.toDomain(): Intake = Intake(
    id = id,
    preparatId = preparat_id,
    duringDay = during_day,
    often = often,
    weekday = weekday
)

private fun Intake.toEntity(): IntakeEntity = IntakeEntity(
    id = id,
    preparat_id = preparatId,
    during_day = duringDay,
    often = often,
    weekday = weekday
)