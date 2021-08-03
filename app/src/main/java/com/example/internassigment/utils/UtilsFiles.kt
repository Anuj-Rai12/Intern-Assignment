package com.example.internassigment.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import javax.inject.Inject

const val TAG = "INTERNSHIP"

class MyDialog :
    androidx.fragment.app.DialogFragment() {
    private val args: MyDialogArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog = AlertDialog.Builder(requireActivity()).setTitle(args.title)
        alterDialog.setMessage(args.message)
            .setPositiveButton("ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        return alterDialog.create()
    }
}

class CustomProgress @Inject constructor(private val customProgressBar: CustomProgressBar) {
    fun hideLoading() {
        customProgressBar.dismiss()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun showLoading(context: Context, string: String?, boolean: Boolean = false) {
        val con = context as Activity
        customProgressBar.show(con, string, boolean)
    }
}