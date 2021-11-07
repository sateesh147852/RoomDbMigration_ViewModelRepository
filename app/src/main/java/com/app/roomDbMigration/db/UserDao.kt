package com.app.roomDbMigration.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.roomDbMigration.model.User

@Dao
interface UserDao {

  @Query("SELECT * FROM User")
  suspend fun getUsers() : List<User>

  @Query("DELETE  FROM User WHERE id = :userId")
  suspend fun deleteUser(userId: Int)

  @Query("UPDATE  User SET f_name = :fName, l_name = :lName , email = :email WHERE id = :userId")
  suspend fun updateUser(userId: Int,fName : String, lName : String, email : String)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insetUser(user: User)



}