package com.example.statuslar.ui.menu

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.example.statuslar.R
import com.example.statuslar.data.source.local.room.entity.WiseManEntity

class ItemMenu(context: Context, view: View, private var data: WiseManEntity) {
    private var popupMenu = PopupMenu(context, view)
    private var listenerUpdate: ((Long) -> Unit)? = null
    private var listenerDelete: ((WiseManEntity) -> Unit)? = null

    init {
        popupMenu.menuInflater.inflate(R.menu.item_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.updateItemMenu -> {
                    listenerUpdate?.invoke(data.id)
                }
                R.id.deleteItemMenu -> {
                    listenerDelete?.invoke(data)
                }
            }
            true
        }
    }

    fun submitUpdateListener(block: (Long) -> Unit) {
        listenerUpdate = block
    }

    fun submitDeleteListener(block: (WiseManEntity) -> Unit) {
        listenerDelete = block
    }

    fun show() {
        if (Build.VERSION.SDK_INT >= 23)
            popupMenu.gravity = Gravity.END
        popupMenu.show()
    }
}