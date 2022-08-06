package com.example.assessment

import android.util.Log
import androidx.lifecycle.LiveData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class MedicationRepository(private val medicationDao: MedicationDao) {

	val calender = Calendar.getInstance().time
	val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
	val formattedDate = dateFormat.format(calender)
	val allList: LiveData<List<MedicationDetails>> = medicationDao.getMedicationHistory()
	
    val todaysMedication : LiveData<MedicationDetails> = medicationDao.getTodaysMedication(formattedDate)

	suspend fun insert(medicationDetails: MedicationDetails) {
		medicationDao.insert(medicationDetails)
	}

	suspend fun updateTakeMedicineIndication(takeMedicineIndication : String , date : String){
		medicationDao.updateTakeMedicineIndication(takeMedicineIndication , date)
	}

	suspend fun update(details : HashMap<String, String?>?,updatedScore : Int ,todaysDate : String) {
		medicationDao.update(details,updatedScore,todaysDate)
	}

}
