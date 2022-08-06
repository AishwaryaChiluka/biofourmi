package com.example.assessment.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment.MedicationDetails
import com.example.assessment.Shitf
import com.example.assessment.databinding.ItemMedicationHistoryBinding


class MedicationHistoryAdapter(private val mList: ArrayList<MedicationDetails>) : RecyclerView.Adapter<MedicationHistoryAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


		val layoutInflater = LayoutInflater.from(parent.context)
		val itemMedicationHistoryBinding : ItemMedicationHistoryBinding = ItemMedicationHistoryBinding.inflate(layoutInflater,parent,false)
		return ViewHolder(itemMedicationHistoryBinding)

	}


	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val medicationHistory = mList[position]
		holder.itemMedicationHistoryBinding.history=medicationHistory
		val details = medicationHistory.medicineDetails
		if(details!!.containsKey(Shitf.Morning.greeting)){
			holder.itemMedicationHistoryBinding.tvMorninigTime.text = details.get(Shitf.Morning.greeting)
			holder.itemMedicationHistoryBinding.tvMorninigGreeting.text = Shitf.Morning.greeting
			holder.itemMedicationHistoryBinding.tvMorninigTime.visibility = View.VISIBLE
			holder.itemMedicationHistoryBinding.tvMorninigGreeting.visibility = View.VISIBLE
		}
		if(details!!.containsKey(Shitf.AfterNoon.greeting)){
			holder.itemMedicationHistoryBinding.tvAfternoonTime.text = details.get(Shitf.AfterNoon.greeting)
			holder.itemMedicationHistoryBinding.tvAfternoonGreeting.text = Shitf.AfterNoon.greeting
			holder.itemMedicationHistoryBinding.tvAfternoonTime.visibility = View.VISIBLE
			holder.itemMedicationHistoryBinding.tvAfternoonGreeting.visibility = View.VISIBLE
		}
		if(details!!.containsKey(Shitf.Evening.greeting)){
			holder.itemMedicationHistoryBinding.tvEveningTime.text = details.get(Shitf.Evening.greeting)
			holder.itemMedicationHistoryBinding.tvEveningGreeting.text = Shitf.AfterNoon.greeting
			holder.itemMedicationHistoryBinding.tvEveningTime.visibility = View.VISIBLE
			holder.itemMedicationHistoryBinding.tvEveningGreeting.visibility = View.VISIBLE
		}
       if(medicationHistory.score == 100 ){
       	holder.itemMedicationHistoryBinding.score.setTextColor(Color.GREEN)
	   }
		else if(medicationHistory.score!! > 50 && medicationHistory.score!! < 100){
		   holder.itemMedicationHistoryBinding.score.setTextColor(Color.YELLOW)
	   }
		else{
		   holder.itemMedicationHistoryBinding.score.setTextColor(Color.RED)
	   }
	}


	override fun getItemCount(): Int {
		return mList.size
	}
    fun updateHistory(history : ArrayList<MedicationDetails>){
    	mList.clear()
		mList.addAll(history)
		notifyDataSetChanged()
    }

	class ViewHolder(var itemMedicationHistoryBinding: ItemMedicationHistoryBinding) : RecyclerView.ViewHolder(itemMedicationHistoryBinding.root) {


	}
}
