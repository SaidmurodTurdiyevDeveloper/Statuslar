package com.example.statuslar.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.statuslar.R
import com.example.statuslar.databinding.ShowMessageBinding

class DialogShowMessage(context: Context, message: String) {
    @SuppressLint("InflateParams")
    private var view = LayoutInflater.from(context).inflate(R.layout.show_message, null, false)
    private var dialog = AlertDialog.Builder(context).setView(view).create()
    private var binding: ShowMessageBinding? = ShowMessageBinding.bind(view)
    private var listener: (() -> Unit)? = null

    init {
        binding?.backbutton?.setOnClickListener {
            dialog.dismiss()
            binding = null
        }
        binding?.actionButton?.setOnClickListener {
            listener?.invoke()
            dialog.dismiss()
            binding = null
        }
        binding?.text?.text = message
    }

    fun setListener(block: () -> Unit) {
        listener = block
    }

    fun show(buttonText: String) {
        binding?.actionButton?.text = buttonText
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
        binding = null
    }
}