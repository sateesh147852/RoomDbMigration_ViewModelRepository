package com.app.roomDbMigration.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomDbMigration.databinding.UserItemBinding
import com.app.roomDbMigration.model.User

class UserAdapter(val updateUser: UpdateUser) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

  var user: List<User> = ArrayList()

  fun setUserData(user: List<User>) {
    this.user = user
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UserViewHolder(binding)
  }

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    holder.binding.tvName.text =
      String.format("%s %s", user[position].f_name, user[position].l_name)
    holder.binding.tvId.text = user[position].id.toString()
    holder.binding.tvEmail.text = user[position].email
    holder.binding.tvAge.text = user[position].age.toString()

    holder.binding.ivDelete.setOnClickListener{
      updateUser.deleteUser(user[position].id)
    }

    holder.binding.ivEdit.setOnClickListener{
      updateUser.updateUser(user[position].id)
    }
  }

  override fun getItemCount(): Int {
    return user?.size ?: 0
  }

  class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

  interface UpdateUser{

    fun deleteUser(userId : Int)

    fun updateUser(userId : Int)

  }



}