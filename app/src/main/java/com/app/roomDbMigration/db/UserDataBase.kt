package com.app.roomDbMigration.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.roomDbMigration.model.User

@Database(entities = [User::class],version = 2)
abstract class UserDataBase : RoomDatabase() {

  abstract fun userDao(): UserDao

  companion object {

    private var userDataBase: UserDataBase? = null

    val Migration1_2 : Migration = object : Migration(1,2) {
      override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER NOT NULL DEFAULT(20)")
      }

    }

    fun getInstance(context: Context): UserDataBase? {
      synchronized(this) {
        if (userDataBase == null) {
          Log.i( "getInstance: ","Room")
          userDataBase = Room.databaseBuilder(context, UserDataBase::class.java, "user")
            .addMigrations(Migration1_2)
            //.fallbackToDestructiveMigration()
            .build()
        }
      }
      return userDataBase
    }

  }
}