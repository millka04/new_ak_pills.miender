package com.example.real_ak_pilsmineder.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.real_ak_pilsmineder.data.local.MyDatabase
import com.example.real_ak_pilsmineder.data.local.dao.MedicationDao
import com.example.real_ak_pilsmineder.data.repository.IntakeRepositoryImpl
import com.example.real_ak_pilsmineder.data.repository.MedicationRepositoryImpl
import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.model.Medication
import com.example.real_ak_pilsmineder.domain.usecase.AddIntakeUseCase
import com.example.real_ak_pilsmineder.domain.usecase.AddMedicationUseCase
import com.example.real_ak_pilsmineder.domain.usecase.DeleteMedicationUseCase
import com.example.real_ak_pilsmineder.domain.usecase.GetIntakesForDateUseCase
import com.example.real_ak_pilsmineder.domain.usecase.GetIntakesUseCase
import com.example.real_ak_pilsmineder.domain.usecase.GetMedicationsUseCase
import com.example.real_ak_pilsmineder.domain.usecase.UpdateMedicationUseCase
import com.example.real_ak_pilsmineder.utils.NotificationUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate





class MedicationViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MyDatabase.getDatabase(application)
    private val medicationRepository = MedicationRepositoryImpl(database.medicationDao())
    private val intakeRepository = IntakeRepositoryImpl(database.intakeDao())


    private val getMedicationsUseCase = GetMedicationsUseCase(medicationRepository)
    private val addMedicationUseCase = AddMedicationUseCase(medicationRepository)

    private val getIntakesUseCase = GetIntakesUseCase(intakeRepository)
    private val addIntakeUseCase = AddIntakeUseCase(intakeRepository)

    private val updateMedicationUseCase = UpdateMedicationUseCase(medicationRepository)
    private val deleteMedicationUseCase = DeleteMedicationUseCase(medicationRepository)

    private val getIntakesForDateUseCase =
        GetIntakesForDateUseCase(intakeRepository, medicationRepository)

    val medications: StateFlow<List<Medication>> =
        getMedicationsUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allIntakes: StateFlow<List<Intake>> =
        getIntakesUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMedication(name: String) {
        viewModelScope.launch {
            addMedicationUseCase(name)
        }
    }
    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            try {
               allIntakes.value
                   .filter { it.preparatId == medication.id }
                   .forEach { intake ->
                       NotificationUtils.cancelAlarm(getApplication(), intake.id)
                   }
                medicationRepository.deleteMedication(medication)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMedication(medication: Medication) {
        viewModelScope.launch {
            Log.d("LLL", "upd: ${medication.name}")
            updateMedicationUseCase(medication.name)
        }
    }

    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            deleteMedicationUseCase(medication)
        }
    }

    fun addOrUpdateIntake(intake: Intake, medicationName: String) {
        viewModelScope.launch {
            if (intake.id == 0L) {
                val newId = intakeRepository.addIntake(intake)
                NotificationUtils.scheduleAlarm(
                    context = getApplication(),
                    intakeId = newId,
                    medicationName = medicationName,
                    hour = intake.duringDay / 60,
                    minute = intake.duringDay % 60
                )
            } else {
                NotificationUtils.cancelAlarm(getApplication(), intake.id)
                NotificationUtils.scheduleAlarm(
                    context = getApplication(),
                    intakeId = intake.id,
                    medicationName = medicationName,
                    hour = intake.duringDay / 60,
                    minute = intake.duringDay % 60
                )
            }
        }
    }
    fun deleteIntake(intake: Intake) {
        viewModelScope.launch {
        }
    }

    suspend fun getIntakesForDate(date: LocalDate) =
        getIntakesForDateUseCase(date)
}