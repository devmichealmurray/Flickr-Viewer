package com.devmmurray.flickrrocket.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.devmmurray.flickrrocket.R

private const val DIALOG_ID = "id"
private const val DIALOG_MESSAGE ="message"
private const val DIALOG_POSITIVE_RID = "positive_rid"
private const val DIALOG_NEGATIVE_RID = "negative_rid"

class AppDialog: AppCompatDialogFragment() {

    private var dialogEvents: DialogEvents? = null

    /**
     *  Dialog callback interfaces for user selected results
     */

    internal interface DialogEvents {
        fun onPositiveDialogResults(dialogId: Int, args: Bundle)
        fun onNegativeDialogResults(dialogId: Int, args: Bundle)
        fun onDialogCancelled(dialogId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val arguments = arguments
        val dialogId: Int
        val messageString: String?
        var positiveStringId: Int
        var negativeStringId: Int

        if (arguments != null) {
            dialogId = arguments.getInt(DIALOG_ID)
            messageString = arguments.getString(DIALOG_MESSAGE)

            if (dialogId == 0 || messageString == null) {
                throw IllegalArgumentException("DIALOG_ID and/or DIALOG_MESSAGE not present in bundle")
            }

            positiveStringId = arguments.getInt(DIALOG_POSITIVE_RID)
            if (positiveStringId == 0) {
                positiveStringId = R.string.ok
            }
            negativeStringId = arguments.getInt(DIALOG_NEGATIVE_RID)
            if (negativeStringId == 0) {
                negativeStringId = R.string.cancel
            }
        } else {
            throw IllegalArgumentException("Must pass DIALOG_ID and DIALOG_MESSAGE in bundle")
        }

        return builder.setMessage(messageString)
            .setPositiveButton(positiveStringId) { dialogInterface, which ->
                dialogEvents?.onPositiveDialogResults(dialogId, arguments)
            }
            .setNegativeButton(negativeStringId) { dialogInterface, which ->
                dialogEvents?.onNegativeDialogResults(dialogId, arguments)
            }
            .create()
    }

    override fun onDetach() {
        super.onDetach()
        dialogEvents = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }
}