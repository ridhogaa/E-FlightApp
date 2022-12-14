package com.c10.finalproject.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c10.finalproject.data.local.database.dao.WishlistDao
import com.c10.finalproject.data.local.database.entity.WishlistEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Database(entities = [WishlistEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao

    companion object {
        private var dbINSTANCE: AppDatabase? = null
        fun getAppDB(context: Context): AppDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MyDB.db"
                ).allowMainThreadQueries().build()
            }
            return dbINSTANCE!!
        }
    }
}