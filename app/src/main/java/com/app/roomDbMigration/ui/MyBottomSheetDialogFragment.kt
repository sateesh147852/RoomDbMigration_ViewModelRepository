package com.app.roomDbMigration.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.roomDbMigration.R
import com.app.roomDbMigration.databinding.BottomSheetModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

  private lateinit var bottomSheetModalBinding: BottomSheetModalBinding
  private lateinit var saveUserData: SaveUserData
  private var isEdit : Boolean = false

  override fun onAttach(context: Context) {
    super.onAttach(context)
    saveUserData = context as SaveUserData
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    isEdit = requireArguments().getBoolean("isEdit")
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    bottomSheetModalBinding = BottomSheetModalBinding.inflate(inflater, container, false)
    initializeView()
    return bottomSheetModalBinding.root
  }

  private fun initializeView() {
    if (isEdit){
      bottomSheetModalBinding.etId.setText(requireArguments().getInt("userId").toString())
      bottomSheetModalBinding.etFirstName.requestFocus()
      bottomSheetModalBinding.etId.isClickable = false
      bottomSheetModalBinding.etId.isEnabled = false
    }
    bottomSheetModalBinding.btClear.setOnClickListener(this)
    bottomSheetModalBinding.btSave.setOnClickListener(this)
  }

  override fun onClick(view: View) {

    when (view.id) {

      bottomSheetModalBinding.btClear.id -> {
        bottomSheetModalBinding.etFirstName.setText("")
        bottomSheetModalBinding.etId.setText("")
        bottomSheetModalBinding.etLastName.setText("")
        bottomSheetModalBinding.etEmail.setText("")
      }

      bottomSheetModalBinding.btSave.id  -> {
        saveUserData.onUserDataSaved(
          bottomSheetModalBinding.etId.text.toString().toInt(),
          bottomSheetModalBinding.etFirstName.text.toString(),
          bottomSheetModalBinding.etLastName.text.toString(),
          bottomSheetModalBinding.etEmail.text.toString(),
          isEdit
        )
      }

    }
  }

  interface SaveUserData {

    fun onUserDataSaved(id: Int, fName: String, lName: String, email: String, isEdit : Boolean)

  }

}