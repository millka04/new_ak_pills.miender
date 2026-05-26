package com.example.real_ak_pilsmineder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.real_ak_pilsmineder.data.local.entity.MedicationEntity
import com.example.real_ak_pilsmineder.domain.model.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM preparat ORDER BY name")
    fun getAll(): Flow<List<MedicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MedicationEntity): Long

}