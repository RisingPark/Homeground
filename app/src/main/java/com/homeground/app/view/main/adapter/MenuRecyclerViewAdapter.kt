package com.homeground.app.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homeground.app.R
import com.homeground.app.view.main.bean.MenuItemDTO
import com.homeground.app.common.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.item_left_menu.view.*

class MenuRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<MenuItemDTO>()
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MenuViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MenuViewHolder).onBind(items[holder.adapterPosition])
    }

    inner class MenuViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_left_menu, parent, false)
    ) {
        fun onBind(items: MenuItemDTO?) {
            itemView.run {
                item_title.text = items?.title
                items?.iconRes?.let { item_icon.setImageResource(it) }
            }

            itemView.setOnClickListener {
                onItemClickListener?.onItemClickListener(itemView, adapterPosition)
            }
        }
    }


}