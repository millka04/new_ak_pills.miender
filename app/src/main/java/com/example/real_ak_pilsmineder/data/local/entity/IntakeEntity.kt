package com.example.real_ak_pilsmineder.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "priem",
    foreignKeys = [ForeignKey(
        entity = MedicationEntity::class,
        parentColumns = ["id"],
        childColumns = ["preparat_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class IntakeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val preparat_id: Long,
    val during_day: Int,
    val often: String,
    val weekday: String
)