package com.example.real_ak_pilsmineder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.real_ak_pilsmineder.data.local.entity.MedicationEntity
import com.example.real_ak_pilsmineder.domain.model.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM preparat ORDER BY name")
    fun getAll(): Flow<List<MedicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MedicationEntity): Long

    @Update
    suspend fun update(entity: MedicationEntity)

    @Delete
    suspend fun delete(entity: MedicationEntity)
}