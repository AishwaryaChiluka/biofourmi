package com.example.assessment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicationViewModel (application: Application) :AndroidViewModel(application) {
	

	val details : LiveData<List<MedicationDetails>>
	val repository : MedicationRepository
	val addMedicationsDetails = MutableLiveData<MedicationDetails>()
	val getTodaysDetails : LiveData<MedicationDetails>

	init {
		val dao = MedicationDatabase.getDatabase(application).getMedicationDao()
		repository = MedicationRepository(dao)
		details = repository.allList
		getTodaysDetails = repository.todaysMedication
	}

	fun medicationTaken(medicationDetails: MedicationDetails) = viewModelScope.launch(Dispatchers.IO) {
		repository.insert(medicationDetails)
	}

  /* fun updateTime(listOfTime : ArrayList<String>,date : String , hashmap : HashMap<String, Boolean?>? )  = viewModelScope.launch(Dispatchers.IO){
   	repository.updateTime(listOfTime,date , hashmap)
   }*/

	fun updateTakeMedicineIndication(takeMedicineIndication : String , date : String)  = viewModelScope.launch(Dispatchers.IO){
		repository.updateTakeMedicineIndication(takeMedicineIndication,date)
	}
	fun update(details : HashMap<String, String?>?,updatedScore : Int ,todaysDate : String ) = viewModelScope.launch(Dispatchers.IO) {
		repository.update(details,updatedScore,todaysDate)
	}

}
