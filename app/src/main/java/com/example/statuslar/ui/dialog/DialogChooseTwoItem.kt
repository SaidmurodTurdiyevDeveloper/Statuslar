package com.example.statuslar.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.statuslar.R
import com.example.statuslar.databinding.ChooseMessageDialogBinding

class DialogChooseTwoItem(context: Context, message: String) {
    @SuppressLint("InflateParams")
    private var view =
        LayoutInflater.from(context).inflate(R.layout.choose_message_dialog, null, false)
    private var dialog = AlertDialog.Builder(context).setView(view).create()
    private var binding: ChooseMessageDialogBinding? = ChooseMessageDialogBinding.bind(view)
    private var listener: (() -> Unit)? = null
    private var listenerTwo: (() -> Unit)? = null

    init {
        binding?.backbutton?.setOnClickListener {
            hide()
        }
        binding?.actionOneButton?.setOnClickListener {
            listener?.invoke()
            hide()
        }
        binding?.actionTwoButton?.setOnClickListener {
            listenerTwo?.invoke()
            hide()
        }
        binding?.text?.text = message
    }

    fun setListenerOne(text: String, block: () -> Unit) {
        binding?.actionOneButton?.text = text
        listener = block
    }

    fun setListenerTwo(text: String, block: () -> Unit) {
        binding?.actionTwoButton?.text = text
        listenerTwo = block
    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
        binding = null
    }
}
