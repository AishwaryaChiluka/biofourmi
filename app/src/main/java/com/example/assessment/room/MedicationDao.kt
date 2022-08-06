package com.example.assessment

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MedicationDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(medicationDetails: MedicationDetails)

	@Query("Select * from MEDICATIONDETAILS")
	fun getMedicationHistory(): LiveData<List<MedicationDetails>>


	@Query("SELECT * FROM MedicationDetails WHERE date =:todaysDate")
	fun getTodaysMedication(todaysDate : String): LiveData<MedicationDetails>


	@Query("UPDATE MedicationDetails SET isMedicationTaken = :takeMedicineIndication WHERE date =:todaysDate")
	fun updateTakeMedicineIndication(takeMedicineIndication : String ,todaysDate : String)


	@Query("UPDATE MedicationDetails SET medicineDetails = :details, score = :updatedScore  WHERE date =:todaysDate")
	suspend fun update(details : HashMap<String, String?>?,updatedScore : Int ,todaysDate : String)
}
