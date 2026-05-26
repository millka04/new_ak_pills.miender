package com.example.real_ak_pilsmineder.domain.usecase


import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.repository.IntakeRepository
import kotlinx.coroutines.flow.Flow

class GetIntakesUseCase(
    private val repository: IntakeRepository
) {
    operator fun invoke(): Flow<List<Intake>> = repository.getAllIntakes()
}