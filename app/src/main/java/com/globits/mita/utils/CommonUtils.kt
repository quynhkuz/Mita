package com.globits.mita.utils

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import com.globits.mita.R

fun showDialogError(context: Context, errorString:String){
    val dialog = Dialog(context)
    dialog.setContentView(R.layout.dialog_show_error)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window?.setBackgroundDrawable(null)
    val btnCancel = dialog.findViewById<TextView>(R.id.confirm_button)
    val error = dialog.findViewById<TextView>(R.id.message)
    error.text= errorString
    btnCancel.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}
