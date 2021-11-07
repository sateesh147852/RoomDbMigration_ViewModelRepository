package com.app.roomDbMigration.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
  @PrimaryKey(autoGenerate = true)
  val id : Int,
  val f_name: String,
  val l_name: String,
  val email: String,
  val age : Int
)
