package com.example.statuslar.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.statuslar.R
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.zZz_utills.extentions.inflate

class AphorizmAdapter : RecyclerView.Adapter<AphorizmAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ITEM_DIFF)
    private var listenerItemClick: ((Long) -> Unit)? = null
    private var listenerItemfavourute: ((SpeachEnity) -> Unit)? = null
    private var listenerItemDelete: ((SpeachEnity) -> Unit)? = null
    private var listenerItemAdd: (() -> Unit)? = null

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<SpeachEnity>() {
            override fun areItemsTheSame(oldItem: SpeachEnity, newItem: SpeachEnity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SpeachEnity, newItem: SpeachEnity): Boolean =
                oldItem.text == newItem.text
        }
    }

    fun submitList(list: List<SpeachEnity>) {
        differ.submitList(list.toMutableList())
    }

    fun setListenerItemClick(block: (Long) -> Unit) {
        listenerItemClick = block
    }

    fun setListenerItemfavourute(block: (SpeachEnity) -> Unit) {
        listenerItemfavourute = block
    }

    fun setListenerItemDelete(block: (SpeachEnity) -> Unit) {
        listenerItemDelete = block
    }

    fun setListenerItemAdd(block: () -> Unit) {
        listenerItemAdd = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_speach)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                if (absoluteAdapterPosition != itemCount - 1)
                    listenerItemClick?.invoke(differ.currentList[absoluteAdapterPosition].id)
            }

            view.findViewById<LottieAnimationView>(R.id.fawouriteItem).apply {
                setOnClickListener {
                    if (absoluteAdapterPosition != itemCount - 1) {
                        if (differ.currentList[absoluteAdapterPosition].favourite == 0) {
                            speed = 2F
                            playAnimation()
                        } else {
                            progress = 0F
                            pauseAnimation()
                        }
                        listenerItemfavourute?.invoke(differ.currentList[absoluteAdapterPosition])
                    }
                }
            }
            view.findViewById<LottieAnimationView>(R.id.deleteitem).apply {
                setOnClickListener {
                    if (absoluteAdapterPosition != itemCount - 1) {
                        speed = 3F
                        playAnimation()
                        listenerItemDelete?.invoke(differ.currentList[absoluteAdapterPosition])
                    }
                }
            }
            view.findViewById<LottieAnimationView>(R.id.addItemLottie).apply {
                setOnClickListener {
                    playAnimation()
                    if (absoluteAdapterPosition == itemCount - 1)
                        listenerItemAdd?.invoke()
                }
            }
        }

        fun bind(data: SpeachEnity) {
            itemView.apply {
                if (bindingAdapterPosition != itemCount - 1) {
                    findViewById<LinearLayout>(R.id.item_speach_layout).visibility = View.VISIBLE
                    findViewById<LinearLayout>(R.id.add_button).visibility = View.GONE
                    findViewById<TextView>(R.id.text_item_speach).text = data.text
                    if (data.favourite == 1)
                        findViewById<LottieAnimationView>(R.id.fawouriteItem).apply {
                            speed = 2F
                            playAnimation()
                        }
                    else
                        with(findViewById<LottieAnimationView>(R.id.fawouriteItem)) {
                            progress = 0F
                            pauseAnimation()
                        }
                } else {
                    findViewById<LinearLayout>(R.id.item_speach_layout).visibility = View.GONE
                    findViewById<LinearLayout>(R.id.add_button).visibility = View.VISIBLE
                    with(findViewById<LottieAnimationView>(R.id.addItemLottie)) {
                        speed = 0.8F
                        playAnimation()
                    }
                }
            }
        }
    }
}
