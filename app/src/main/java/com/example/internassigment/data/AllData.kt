package com.example.internassigment.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "USERS_INFO")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val phone: String
) : Parcelable


@Entity(tableName = "COURSE_INFO_TABLE")
@Parcelize
data class CourseName(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseName: String,
    val week: Int,
    val thumbnails: Int,
    val courseSelected: Boolean,
    val totalTime: Int = 0,
    val whyChoose: List<WhyChoose>? = null
) : Parcelable


@Parcelize
data class WhyChoose(
    val title: String,
    val description: String,
) : Parcelable


sealed class AllData {
    data class Course(
        val courseName: CourseName
    ) : AllData()

    data class Users(
        val user: User
    ) : AllData()
}
