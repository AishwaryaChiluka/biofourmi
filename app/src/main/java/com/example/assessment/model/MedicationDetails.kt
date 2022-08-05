package com.example.assessment

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.HashMap


@Entity
data class MedicationDetails(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "ID")val id :Int?,
    @NonNull
    val date: String?,
    val score: Int?,
    var  medicineDetails: HashMap<String, String?>? ,
    var  isMedicationTaken : String?
   /* val greeting:ArrayList<String>?,
    val time: ArrayList<String>?,*/
    )


object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
object MapTypeConverter {

    @TypeConverter
    fun fromHashMap(value: String?): HashMap<String, String?>? {
        val listType = object : TypeToken<HashMap<String, String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun mapToString(value: HashMap<String, String?>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}

