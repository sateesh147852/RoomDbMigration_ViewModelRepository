package com.app.roomDbMigration.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.roomDbMigration.ropository.UserRepository

class ViewModelRepository(val userRepository: UserRepository) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return MainViewModel(userRepository) as T
  }
}