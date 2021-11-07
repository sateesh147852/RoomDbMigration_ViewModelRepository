package com.app.roomDbMigration.ropository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.roomDbMigration.db.UserDao
import com.app.roomDbMigration.model.User

class UserRepository(private val userDao: UserDao) {

  private var liveData = MutableLiveData<List<User>>()

  suspend fun insertUser(user: User){
    userDao.insetUser(user)
    getUsers()
  }

  suspend fun deleteUser(userId: Int){
    userDao.deleteUser(userId)
    getUsers()
  }

  suspend fun updateUser(userId: Int,fName : String, lName : String, email : String){
    userDao.updateUser(userId, fName, lName, email)
    getUsers()
  }

  suspend fun getUsers() : LiveData<List<User>>{
    liveData.postValue(userDao.getUsers())
    return liveData
  }



  fun getUpdatedUsers() : LiveData<List<User>>{
    return liveData
  }



}