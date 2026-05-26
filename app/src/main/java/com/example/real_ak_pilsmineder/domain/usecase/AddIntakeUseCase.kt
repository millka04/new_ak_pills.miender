package com.example.real_ak_pilsmineder.domain.usecase

import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.repository.IntakeRepository


class AddIntakeUseCase(
    private val repository: IntakeRepository
) {
    suspend operator fun invoke(intake: Intake) {
        repository.addIntake(intake)
    }
}