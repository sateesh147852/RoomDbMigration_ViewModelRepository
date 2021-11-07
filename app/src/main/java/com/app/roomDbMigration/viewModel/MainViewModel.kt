package com.app.roomDbMigration.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomDbMigration.model.User
import com.app.roomDbMigration.ropository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(val userRepository: UserRepository) : ViewModel() {

  init {
    viewModelScope.launch {
      userRepository.getUsers()
    }
  }

  fun addUser(user: User){
    viewModelScope.launch {
      userRepository.insertUser(user)
    }
  }

  fun updateUser(userId: Int,fName : String, lName : String, email : String){
    viewModelScope.launch {
      userRepository.updateUser(userId, fName, lName, email)
    }
  }

  fun getUsers(): LiveData<List<User>> {
    return userRepository.getUpdatedUsers()
  }

  fun deleteUser(userId: Int) {
    viewModelScope.launch {
      userRepository.deleteUser(userId)
    }
  }

}