package com.example.internassigment.db

import androidx.room.*
import com.example.internassigment.data.CourseName


@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseName: CourseName)

    @Query("select * from COURSE_INFO_TABLE")
    suspend fun getCourseInfo(): List<CourseName>

    @Update
    suspend fun updateCourseInfo(courseName: CourseName)

    @Query("select * From COURSE_INFO_TABLE where (courseSelected ==:hideComplete Or courseSelected = 1)")
    suspend fun getCourseSelectedByUsers(hideComplete: Boolean = true): List<CourseName>

}