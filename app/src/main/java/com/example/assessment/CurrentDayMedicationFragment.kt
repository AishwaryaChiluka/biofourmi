package com.example.assessment


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assessment.databinding.FragmentCurrentDayMedicationBinding
import java.text.SimpleDateFormat
import java.util.*


class CurrentDayMedicationFragment : Fragment() {

    private var _binding: FragmentCurrentDayMedicationBinding? = null

    private val binding get() = _binding!!
    private lateinit var medicationViewModel : MedicationViewModel
    var score : Int? =0
    var c = Calendar.getInstance()
    var list : List<MedicationDetails> =   listOf<MedicationDetails>()
    lateinit var todaysMedication : MedicationDetails
    var text : String? = null
    lateinit var formattedDate: String
    var hashMap : HashMap<String, String?>?
            = HashMap<String, String?> ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentDayMedicationBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        observeTodaysmedication()
        observeListofMedication()
        createNotificationChannel()
        setnotification()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        val calender = Calendar.getInstance().time
            addMedication(timeOfDay , calender)

    }

    private fun setTodaysMedicatonDetails() {
        todaysMedication.date?.let{
            for(key in todaysMedication.medicineDetails!!.keys){
                hashMap?.put(key, todaysMedication.medicineDetails!!.get(key))
            }
            val k = todaysMedication.medicineDetails!!.keys
            score = todaysMedication.score
            _binding!!.greeting.text = k.elementAt(k.size-1)
            if(!(hashMap!!.containsKey(text))) {
                medicationViewModel.updateTakeMedicineIndication(getString(R.string.takeMedicine),formattedDate)
            }else{
                medicationViewModel.updateTakeMedicineIndication(getString(R.string.medicinetaken),formattedDate)
            }
        }

    }

    private fun observeListofMedication() {
        medicationViewModel.details.observe(
            requireActivity(),  {
                    medication -> medication?.let {
                list = medication
            }
            }
        )
    }

    private fun observeTodaysmedication() {
        val emptyMedication = MedicationDetails(null,null,0,null,getString(R.string.takeMedicine))
        medicationViewModel.getTodaysDetails.observe(
            requireActivity(),{
             medication -> todaysMedication = medication?:emptyMedication
                _binding!!.mediction =  todaysMedication
                setTodaysMedicatonDetails()

            }
        )

    }

    private fun addMedication(timeOfDay: Int, calender: Date) {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        formattedDate = dateFormat.format(calender)
        val formatTime = SimpleDateFormat("hh:mm aa")
        val formattedTime = formatTime.format(
           calender
        )
        setnotification()

        if (timeOfDay >= 0 && timeOfDay < 12) {
            score = score?.plus(Shitf.Morning.score)
            text = Shitf.Morning.greeting

        }
        else if (timeOfDay >= 12 && timeOfDay < 16) {
            score = score?.plus(Shitf.AfterNoon.score)
            text = Shitf.AfterNoon.greeting
        }
        else if (timeOfDay >= 16 && timeOfDay < 24) {
            score = score?.plus(Shitf.Evening.score)
            text = Shitf.Evening.greeting
        }
        _binding!!.greeting.text = text
        hashMap?.put(text!!,formattedTime)
        val medicationDetails = MedicationDetails( null,formattedDate, score ,hashMap , getString(R.string.TakenMedicine) )
        _binding!!.addMedicine.setOnClickListener {
         todaysMedication.date?.let {
             if (list.contains(todaysMedication)) {
                 Log.d("text","text "+text)
                 Log.d("text","text "+hashMap)
                 if ((todaysMedication.medicineDetails!!.containsKey(text))) {
                     Toast.makeText(context, "Already updated", Toast.LENGTH_SHORT).show()
                 } else {
                     medicationViewModel.update(hashMap,score!!,formattedDate)
                     Log.d("text","medicaion "+medicationDetails)
                 }
             }
         }?:medicationViewModel.medicationTaken(medicationDetails)
            }
    }

    private fun setViewModel() {
        medicationViewModel = ViewModelProvider(this).get(MedicationViewModel::class.java)
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("2", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun setnotification() {
        val am = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, NotificationReceiver::class.java)
        val sender = PendingIntent.getBroadcast(
            context,0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 40);
        c.set(Calendar.SECOND, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                alarmIntent.getLongExtra("intervalMillis", 0).toLong(),
                sender
            )
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.history -> {
                Toast.makeText(context, "History", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }*/

    private fun navigateToHistory(): Boolean {
      findNavController().navigate(R.id.action_currentDayMedicationFragment_to_medicationHistoryFragment)
        return true
    }

}

enum class Shitf(val greeting  : String , val score : Int){
    Morning("Good morning",30),
    AfterNoon("Good AfterNoon",30),
    Evening("Good Evening ",40)
}