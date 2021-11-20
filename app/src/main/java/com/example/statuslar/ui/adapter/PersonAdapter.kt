package com.example.statuslar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statuslar.R
import com.example.statuslar.data.model.Person
import com.example.statuslar.databinding.PersonItemBinding
import com.example.statuslar.zZz_utills.extentions.inflate
import de.hdodenhof.circleimageview.CircleImageView

class PersonAdapter(private val list: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    private var listenerItemClick: ((Person) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.person_item)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun setListener(block: ((Person) -> Unit)) {
        listenerItemClick = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listenerItemClick?.invoke(list[absoluteAdapterPosition])
            }
        }

        fun bind(data: Person) {
            with(itemView) {
                findViewById<TextView>(R.id.textPerson).text = data.name
                findViewById<CircleImageView>(R.id.imageperson).setImageResource(data.resId)
            }
        }
    }

}