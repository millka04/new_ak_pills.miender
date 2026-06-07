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
        if (intake.often == "once") {
            return intake.date == date
        }
        else if (intake.often == "everyday") {
            if ((intake.date != null) && (intake.date <= date) && (date <= intake.date.plusDays((intake.length-1).toLong()))){
                return true
            }
            else { return false }
        }
        else if (intake.often == "everymonth") {
            val start = intake.date
            if (start == null) {
                return false
            }
            else {
                val monthsBetween = (date.year - start.year) * 12 + (date.monthValue - start.monthValue)
                if (monthsBetween < 0) {
                    return false
                }
                else {
                    val cycleStart = start.plusMonths(monthsBetween.toLong())
                    val cycleEnd =
                        cycleStart.plusDays((intake.length - 1).toLong())

                    if ((date >= cycleStart) && (date <= cycleEnd)) {
                        return true
                    }
                    else { return false }
                }
            }
        }

        if (intake.weekday.length != 7) return false
        val dayIndex = when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> 0
            DayOfWeek.TUESDAY -> 1
            DayOfWeek.WEDNESDAY -> 2
            DayOfWeek.THURSDAY -> 3
            DayOfWeek.FRIDAY -> 4
            DayOfWeek.SATURDAY -> 5
            DayOfWeek.SUNDAY -> 6
        }
        if (intake.weekday[dayIndex] != '1') return false
        return when (intake.often.lowercase()) {
            "every", "everyweek" -> true
            else -> false
        }
    }
}