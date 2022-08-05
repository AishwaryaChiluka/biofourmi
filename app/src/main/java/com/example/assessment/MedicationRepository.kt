package com.example.assessment

import android.util.Log
import androidx.lifecycle.LiveData



class MedicationRepository(private val medicationDao: MedicationDao) {
	

	val allList: LiveData<List<MedicationDetails>> = medicationDao.getMedicationHistory()
	
    val todaysMedication : LiveData<MedicationDetails> = medicationDao.getTodaysMedication()

	suspend fun insert(medicationDetails: MedicationDetails) {
		medicationDao.insert(medicationDetails)
	}
/*	suspend fun updateTime(listOfTime : ArrayList<String>,date : String , hashmap : HashMap<String, Boolean?>?){
		medicationDao.updateTime(listOfTime,date, hashmap)
	}*/
	suspend fun updateTakeMedicineIndication(takeMedicineIndication : String , date : String){
		medicationDao.updateTakeMedicineIndication(takeMedicineIndication , date)
	}

	suspend fun update(details : HashMap<String, String?>?,updatedScore : Int ,todaysDate : String) {
		medicationDao.update(details,updatedScore,todaysDate)
	}

}
