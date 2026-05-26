package com.example.real_ak_pilsmineder.domain.usecase


import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.model.IntakeWithMed
import com.example.real_ak_pilsmineder.domain.repository.IntakeRepository
import com.example.real_ak_pilsmineder.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate


class GetIntakesForDateUseCase(
    private val intakeRepository: IntakeRepository,
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(date: LocalDate): List<IntakeWithMed> {
        val allIntakes = intakeRepository.getAllIntakes().first()
        val allMedications = medicationRepository.getAllMedications().first()
        val medMap = allMedications.associateBy { it.id }

        return allIntakes
            .mapNotNull { intake ->
                medMap[intake.preparatId]?.let { medication ->
                    if (isApplicable(intake, date)) {
                        IntakeWithMed(intake, medication)
                    } else null
                }
            }
            .sortedBy { it.intake.duringDay }
    }

    private fun isApplicable(intake: Intake, date: LocalDate): Boolean {

        // 1. Проверяем, что строка weekday корректная
        if (intake.weekday.length != 7) return false

        // 2. Определяем индекс дня недели (0 = Понедельник)
        val dayIndex = when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> 0
            DayOfWeek.TUESDAY -> 1
            DayOfWeek.WEDNESDAY -> 2
            DayOfWeek.THURSDAY -> 3
            DayOfWeek.FRIDAY -> 4
            DayOfWeek.SATURDAY -> 5
            DayOfWeek.SUNDAY -> 6
        }

        // 3. Проверяем, выбран ли этот день недели
        if (intake.weekday[dayIndex] != '1') return false

        // 4. Проверяем частоту
        return when (intake.often.lowercase().trim()) {
            "everyday", "every" -> true
            "everyweek" -> true
            "everymonth" -> true                    // упрощённо
            "once" -> false                         // одноразовые пока не поддерживаем
            else -> false
        }
    }
}