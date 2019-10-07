package com.homeground.app.view.point.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homeground.app.R
import com.homeground.app.common.Utils
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import kotlinx.android.synthetic.main.layout_point_history_item.view.*

class PointHistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<PointInfoResponseDTO> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = PointHistoryViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PointHistoryViewHolder).onBind(items[holder.adapterPosition])
    }

    inner class PointHistoryViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_point_history_item, parent, false)
    ){
        fun onBind(item: PointInfoResponseDTO) {
            itemView.run {
//                val color = if (item?.state.equals("+")) Color.RED else Color.BLUE
//                point_history_point_text.setTextColor(color)
                val resultPointText = "${item?.state}${Utils.addCommaP(item?.point)}"
                point_history_point_text.text = resultPointText
                point_history_date_text.text = item?.date
            }
        }
    }
}