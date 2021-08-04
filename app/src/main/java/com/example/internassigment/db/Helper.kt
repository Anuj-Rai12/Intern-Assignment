package com.example.internassigment.db

import androidx.room.TypeConverter
import com.example.internassigment.data.WhyChoose
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Helper {
    /*fun convertToString(bmp: List<WhyChoose>): String? {
        val gson = Gson()
        return gson.toJson(bmp)
    }

    // Deserialize to single object.
    fun convertToObject(jsonString: String?): List<WhyChoose?> {
        val gson = Gson()
        val obj= mutableListOf<WhyChoose?>()
        jsonString.forEach {
            obj.add(gson.fromJson(it, WhyChoose::class.java))
        }
        return  obj
    }*/
    @TypeConverter
    fun fromCountryLangList(countryLang: List<WhyChoose>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WhyChoose?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): List<WhyChoose>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WhyChoose?>?>() {}.type
        return gson.fromJson<List<WhyChoose>>(countryLangString, type)
    }

}