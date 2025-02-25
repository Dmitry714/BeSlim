package com.ctrlz.beslim.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

public class DialogBuilder {
    fun infoDialog(title: String, message: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Добро") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}