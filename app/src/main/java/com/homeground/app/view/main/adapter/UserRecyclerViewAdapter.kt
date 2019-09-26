package com.homeground.app.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.homeground.app.R
import com.risingpark.laddergame.common.OnItemClickListener
import kotlinx.android.synthetic.main.item_user_text.view.*

class UserRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<String>()
    var onItemClickListener: OnItemClickListener? = null
    var lastClickedPosition = -1
    var isAllResult = false
    var colors = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = UserViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).onBind(items[holder.adapterPosition])
    }

    fun setBgColor(position: Int) {
        isAllResult = false
        lastClickedPosition = position
        notifyDataSetChanged()
    }

    fun showResult(colors: ArrayList<Int>) {
        this.colors = colors
        isAllResult = true
        notifyDataSetChanged()
    }

    fun reset(){
        lastClickedPosition = -1
        isAllResult = false
        notifyDataSetChanged()
    }

    inner class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user_text, parent, false)
    ) {
        fun onBind(title: String?) {
            itemView.run {
                item_user_title.text = title
            }

            itemView.setOnClickListener {
                onItemClickListener?.onItemClickListener(itemView, adapterPosition)
            }
            if (lastClickedPosition == adapterPosition)
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
            else
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))

            if (isAllResult){
                itemView.setBackgroundColor(colors[adapterPosition])
            }

        }
    }


}