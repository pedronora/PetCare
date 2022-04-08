package com.project.petcare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.petcare.data.interfaces.PetDao
import com.project.petcare.data.interfaces.VacinaDao
import com.project.petcare.model.Pet
import com.project.petcare.model.Vacina

@Database(
    entities = [Pet::class, Vacina::class],
    version = 1,
    exportSchema = false
)
abstract class PetCareDatabase : RoomDatabase() {

    abstract fun petDao() : PetDao
    abstract fun vacinaDao() : VacinaDao

    companion object {
        @Volatile
        private var INSTANCE: PetCareDatabase? = null

        fun getDatabase(context: Context): PetCareDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetCareDatabase::class.java,
                    "petcare_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}