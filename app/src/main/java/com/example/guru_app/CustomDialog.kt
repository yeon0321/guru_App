package com.example.guru_app

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CustomDialog(context: Context)
{
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener


    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {

        dialog.setContentView(R.layout.ex_diary_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val edit_name = dialog.findViewById<EditText>(R.id.diary_edit)

        dialog.findViewById<Button>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.ok).setOnClickListener {
            onClickListener.onClicked(edit_name.text.toString())
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }

}