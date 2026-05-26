package com.example.real_ak_pilsmineder.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.real_ak_pilsmineder.data.local.dao.IntakeDao
import com.example.real_ak_pilsmineder.data.local.dao.MedicationDao
import com.example.real_ak_pilsmineder.data.local.entity.IntakeEntity
import com.example.real_ak_pilsmineder.data.local.entity.MedicationEntity


@Database(
    entities = [MedicationEntity::class, IntakeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
    abstract fun intakeDao(): IntakeDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "medication_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}