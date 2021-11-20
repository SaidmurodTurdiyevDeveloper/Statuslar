package com.example.statuslar.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statuslar.R
import com.example.statuslar.data.model.Person
import com.example.statuslar.databinding.PersonFragmentBinding
import com.example.statuslar.ui.adapter.PersonAdapter
import com.example.statuslar.zZz_utills.PersonList

class PersonDialog(context: Context) {
    @SuppressLint("InflateParams")
    private var view = LayoutInflater.from(context).inflate(R.layout.person_fragment, null, false)
    private var dialog = AlertDialog.Builder(context).setView(view).create()
    private var binding: PersonFragmentBinding? = PersonFragmentBinding.bind(view)
    private var listenerActive: ((Person) -> Unit)? = null
    private var listenerGallery: (() -> Unit)? = null
    private var list = PersonList().getList()
    private var adapter = PersonAdapter(list)

    init {
        binding?.backbutton?.setOnClickListener {
            dialog.dismiss()
            binding = null
        }
        binding?.addGallagy?.setOnClickListener {
            listenerGallery?.invoke()
            dialog.dismiss()
        }
        adapter.setListener {
            listenerActive?.invoke(it)
            dialog.dismiss()
        }
        binding?.list?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding?.list?.adapter = adapter
    }

    fun submitOpenGallery(block: () -> Unit) {
        listenerGallery = block
    }

    fun submitListner(block: (Person) -> Unit) {
        listenerActive = block
    }

    fun show() {
        dialog.show()
    }
}