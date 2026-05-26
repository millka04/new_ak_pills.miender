package com.example.real_ak_pilsmineder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.real_ak_pilsmineder.data.local.entity.IntakeEntity
import com.example.real_ak_pilsmineder.data.local.entity.MedicationEntity
import com.example.real_ak_pilsmineder.domain.model.Intake
import kotlinx.coroutines.flow.Flow

@Dao
interface IntakeDao {
    @Query("SELECT * FROM priem")
    fun getAll(): Flow<List<IntakeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: IntakeEntity): Long

    @Delete
    suspend fun delete(entity: IntakeEntity)
}