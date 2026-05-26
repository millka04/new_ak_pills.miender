package com.example.real_ak_pilsmineder.domain.usecase

import com.example.real_ak_pilsmineder.domain.model.Medication
import com.example.real_ak_pilsmineder.domain.repository.MedicationRepository

class UpdateMedicationUseCase(
    private val repository: MedicationRepository
) {
    suspend operator fun invoke(name: String) {
        repository.updateMedication(
            Medication(name = name)
        )
    }
}