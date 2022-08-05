package com.example.assessment

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MedicationDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(medicationDetails: MedicationDetails)

	@Query("Select * from MEDICATIONDETAILS")
	fun getMedicationHistory(): LiveData<List<MedicationDetails>>


	@Query("SELECT * FROM MedicationDetails ORDER BY date DESC LIMIT 1")
	fun getTodaysMedication(): LiveData<MedicationDetails>

	/*//@Query("UPDATE MedicationDetails SET time = :listOfTime  WHERE date =:todaysDate")
	@Query("UPDATE MedicationDetails SET time = :listOfTime, isMedicineTaken = :hashmap WHERE date =:todaysDate--+6-+9-``+65")
	fun updateTime(listOfTime : ArrayList<String> , todaysDate : String , hashmap : HashMap<String, Boolean?>?)

	@Query("UPDATE MedicationDetails SET greeting = :listOfGreeting WHERE date =:todaysDate")
	fun updateGreeting(listOfGreeting : ArrayList<String> , todaysDate : String)*/

	@Query("UPDATE MedicationDetails SET isMedicationTaken = :takeMedicineIndication WHERE date =:todaysDate")
	fun updateTakeMedicineIndication(takeMedicineIndication : String ,todaysDate : String)


	@Query("UPDATE MedicationDetails SET medicineDetails = :details, score = :updatedScore  WHERE date =:todaysDate")
	suspend fun update(details : HashMap<String, String?>?,updatedScore : Int ,todaysDate : String)
}
