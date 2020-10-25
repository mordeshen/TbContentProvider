package com.e.tbcontentprovider.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.e.tbcontentprovider.model.ModelContent

@Database(entities = [ModelContent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "tb_db"
        var appDatabase: AppDatabase? = null

        fun getDatabase(mContext: Context): AppDatabase {

            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    mContext.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return appDatabase as AppDatabase

        }
    }

    abstract fun getMainDao(): MainDao

}