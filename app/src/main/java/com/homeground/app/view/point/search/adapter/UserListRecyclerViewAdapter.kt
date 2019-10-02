package com.homeground.app.view.point.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.homeground.app.R
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class UserListRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<UserInfoResponseDTO>()
    var onPointSaveClickListener: OnItemClickListener? = null
    var onPointUesClickListener: OnItemClickListener? = null
    var onPointHistoryClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = UserViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).onBind(items[holder.adapterPosition])
    }

    inner class UserViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_user_list_item, parent, false)
    ){
        fun onBind(items: UserInfoResponseDTO?) {
            itemView.run {
                user_list_name_text.text = items?.name
                user_list_birthday_text.text = if (!Utils.isEmpty(items?.birthday)) "(${items?.birthday})" else ""
                user_list_phone_text.text = items?.phone
                user_list_point_text.text = Utils.addCommaP(items?.point)
                user_list_point_save_text.setOnClickListener { onPointSaveClickListener?.onItemClickListener(itemView, adapterPosition) }
                user_list_point_use_text.setOnClickListener { onPointUesClickListener?.onItemClickListener(itemView, adapterPosition) }
                user_list_point_history_text.setOnClickListener { onPointHistoryClickListener?.onItemClickListener(itemView, adapterPosition) }
            }
        }
    }
}