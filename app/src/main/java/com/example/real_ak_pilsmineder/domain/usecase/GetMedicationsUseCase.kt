package com.example.real_ak_pilsmineder.domain.usecase

import com.example.real_ak_pilsmineder.domain.model.Medication
import com.example.real_ak_pilsmineder.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class GetMedicationsUseCase(
    private val repository: MedicationRepository
) {
    operator fun invoke(): Flow<List<Medication>> = repository.getAllMedications()
}