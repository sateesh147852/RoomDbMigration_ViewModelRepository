package com.app.roomDbMigration.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.roomDbMigration.adapter.UserAdapter
import com.app.roomDbMigration.databinding.ActivityMainBinding
import com.app.roomDbMigration.db.UserDataBase
import com.app.roomDbMigration.model.User
import com.app.roomDbMigration.ropository.UserRepository
import com.app.roomDbMigration.viewModel.MainViewModel
import com.app.roomDbMigration.viewModel.ViewModelRepository

class MainActivity : AppCompatActivity(), MyBottomSheetDialogFragment.SaveUserData,
  UserAdapter.UpdateUser {

  private lateinit var binding: ActivityMainBinding
  private lateinit var userRepository: UserRepository
  private lateinit var mainViewModel: MainViewModel
  private lateinit var viewModelRepository: ViewModelRepository
  private lateinit var userAdapter: UserAdapter
  private lateinit var myBottomSheetDialogFragment: MyBottomSheetDialogFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initialize()
    initializeRecyclerView()
    setOnClickListener()
  }

  private fun setOnClickListener() {
    binding.btAdd.setOnClickListener {
      myBottomSheetDialogFragment = MyBottomSheetDialogFragment()
      val bundle = Bundle()
      bundle.putBoolean("isEdit", false)
      myBottomSheetDialogFragment.arguments = bundle
      myBottomSheetDialogFragment.show(supportFragmentManager, "bottomSheet")
    }
  }

  private fun initializeRecyclerView() {
    userAdapter = UserAdapter(this)
    binding.rvUser.layoutManager = LinearLayoutManager(this)
    binding.rvUser.adapter = userAdapter

    val itemTouchHelperCallback =
      object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
          recyclerView: RecyclerView,
          viewHolder: RecyclerView.ViewHolder,
          target: RecyclerView.ViewHolder
        ): Boolean {

          return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          mainViewModel.deleteUser(userAdapter.user[viewHolder.adapterPosition].id)

        }
      }

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(binding.rvUser)
  }

  private fun initialize() {
    userRepository = UserRepository(UserDataBase.getInstance(this)!!.userDao())
    viewModelRepository = ViewModelRepository(userRepository)
    mainViewModel = ViewModelProvider(this, viewModelRepository)[MainViewModel::class.java]

    mainViewModel.getUsers().observe(this, Observer {
      Toast.makeText(this, it.size.toString(), Toast.LENGTH_SHORT).show()
      userAdapter.setUserData(it)
      userAdapter.notifyDataSetChanged()
    })
  }

  override fun onUserDataSaved(
    id: Int,
    fName: String,
    lName: String,
    email: String,
    isEdit: Boolean
  ) {
    myBottomSheetDialogFragment.dismiss()

    if (isEdit)
      mainViewModel.updateUser(id, fName, lName, email)
    else
      mainViewModel.addUser(User(id, fName, lName, email,50))

  }

  override fun deleteUser(userId: Int) {
    mainViewModel.deleteUser(userId)
  }

  override fun updateUser(userId : Int) {
    myBottomSheetDialogFragment = MyBottomSheetDialogFragment()
    val bundle = Bundle()
    bundle.putBoolean("isEdit", true)
    bundle.putInt("userId", userId)
    myBottomSheetDialogFragment.arguments = bundle
    myBottomSheetDialogFragment.show(supportFragmentManager, "bottomSheet")
  }
}