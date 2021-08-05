package com.example.internassigment.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.di.ApplicationContextDb
import com.example.internassigment.utils.TAG
import com.example.internassigment.utils.UtilsFiles
import com.example.internassigment.utils.UtilsFiles.Companion.whyChoose
import com.example.internassigment.utils.rand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class, CourseName::class], version = 1, exportSchema = false)
@TypeConverters(Helper::class)
abstract class MyRoomDatabaseInstance : RoomDatabase() {
    abstract fun getCourseDao(): CourseDao
    abstract fun getUserDao(): UserDao
    companion object {
        const val My_Db_Name = "RoomDatabaseName"
    }

    class MyCallBack @Inject constructor(
        private val roomDatabaseInstance: Provider<MyRoomDatabaseInstance>,
        @ApplicationContextDb private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val data = roomDatabaseInstance.get().getCourseDao()
            applicationScope.launch {
                UtilsFiles.resFile.forEach { map ->
                    CourseName(
                        id = 0,
                        courseName = map.key,
                        courseSelected = false,
                        week = rand(),
                        thumbnails = map.value,
                        whyChoose = whyChoose[map.key]
                    ).also {courseName ->
                        data.insertCourse(courseName)
                    }
                }
                Log.i(TAG, "onCreate: All Data Submit!!!")
            }
        }
    }
}