package com.example.statuslar.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.statuslar.R
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.ui.menu.ItemMenu
import com.example.statuslar.zZz_utills.PersonList
import com.example.statuslar.zZz_utills.extentions.inflate
import com.squareup.picasso.Picasso
import java.io.File

class RecyclerViewAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val differ = AsyncListDiffer(this, ITEM_DIFF)
    private var listenerItemClick: ((Long) -> Unit)? = null
    private var listenerItemLongClick: ((ItemMenu) -> Unit)? = null
    private var personList = PersonList()

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<WiseManEntity>() {
            override fun areItemsTheSame(oldItem: WiseManEntity, newItem: WiseManEntity): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: WiseManEntity,
                newItem: WiseManEntity
            ): Boolean =
                oldItem.hashtag == newItem.hashtag && oldItem.name == newItem.name && oldItem.info == newItem.info
        }
    }

    fun submitList(list: List<WiseManEntity>) {
        differ.submitList(list.toMutableList())
    }

    fun setListenerItemClick(block: (Long) -> Unit) {
        listenerItemClick = block
    }

    fun setListenerItemLongClick(block: (ItemMenu) -> Unit) {
        listenerItemLongClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = parent.inflate(R.layout.item)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listenerItemClick?.invoke(differ.currentList[bindingAdapterPosition].id)
            }
            itemView.setOnLongClickListener {
                listenerItemLongClick?.invoke(
                    ItemMenu(
                        context,
                        itemView.findViewById(R.id.itemMenu),
                        differ.currentList[bindingAdapterPosition]
                    )
                )
                true
            }
        }

        fun bind(data: WiseManEntity) {
            with(itemView) {
                try {
                    if (data.pathId != "")
                        Picasso.get().load(File(data.pathId)).centerCrop().resize(300, 300).error(R.drawable.somebody)
                            .into(findViewById<ImageView>(R.id.image_item))
                    else
                        findViewById<ImageView>(R.id.image_item).setImageResource(personList.getWithId(data.imageId).resId)
                } catch (e: Exception) {
                    findViewById<ImageView>(R.id.image_item).setImageResource(R.drawable.wise_man)
                }
                findViewById<TextView>(R.id.name_item).text = data.name
                findViewById<TextView>(R.id.hashtag_item).text = data.hashtag
            }
        }
    }
}