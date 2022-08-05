package com.example.assessment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = arrayOf(MedicationDetails::class), version = 1, exportSchema = false)
@TypeConverters(MapTypeConverter::class)
abstract class MedicationDatabase : RoomDatabase() {

	abstract fun getMedicationDao(): MedicationDao

	companion object {
		@Volatile
		private var INSTANCE: MedicationDatabase? = null

		fun getDatabase(context: Context): MedicationDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					MedicationDatabase::class.java,
					"Bioformics_database"
				).build()
				INSTANCE = instance
				instance
			}
		}
	}
}
