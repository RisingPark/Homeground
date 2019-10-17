package com.homeground.app.view.point.save.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.homeground.app.R
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import kotlinx.android.synthetic.main.layout_point_history_item.view.*
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class PointHistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<PointInfoResponseDTO>? = ArrayList<PointInfoResponseDTO>()
    var onPointCancelClickListener: OnItemClickListener? = null
    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = PointHistoryViewHolder(parent).apply {
        mContext = parent.context
    }

    override fun getItemCount(): Int = items?.size!!

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items?.toArray()?.get(holder.adapterPosition) is HashMap<*,*>){
            val map = items?.toArray()?.get(holder.adapterPosition) as HashMap<*,*>
            (holder as PointHistoryViewHolder).onBind(map)
        } else if (items?.toArray()?.get(holder.adapterPosition) is PointInfoResponseDTO){
            val map = items?.get(holder.adapterPosition) as PointInfoResponseDTO
            (holder as PointHistoryViewHolder).onBind(map)
        }
    }

    inner class PointHistoryViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_point_history_item, parent, false)
    ){
        fun onBind(item: HashMap<*,*>?) {
            itemView.run {
                var stateStr = if (item?.get("state").toString() == "+" || item?.get("state").toString() == "!+") "적립" else "사용"
                if(item?.get("state").toString().contains("!"))
                    stateStr = "$stateStr 취소"

                val state = if (item?.get("state").toString() == "+" || item?.get("state").toString() == "!+") "+" else "-"
                val color = if(item?.get("state").toString().contains("!")) R.color.grey else if (state == "+") R.color.red else R.color.blue

                mContext?.let { ContextCompat.getColor(it, color)}?.let {
                    point_history_point_text.setTextColor(it)
                }
                val resultPointText = "${state}${Utils.addCommaP(item?.get("point").toString())}"
                point_history_point_text.text = resultPointText
                point_history_date_text.text = item?.get("date").toString()
                point_history_device_text.text = item?.get("device")?.toString()
                point_history_cancel_text.setOnClickListener { onPointCancelClickListener?.onItemClickListener(itemView, adapterPosition) }
                point_history_swipe_layout.isSwipeEnabled = !item?.get("state").toString().contains("!")
                point_history_state_text.text = stateStr
            }
        }
        fun onBind(item: PointInfoResponseDTO?) {
            itemView.run {
                var stateStr = if (item?.state.toString() == "+" || item?.state.toString() == "!+") "적립" else "사용"
                if(item?.state.toString().contains("!"))
                    stateStr = "$stateStr 취소"

                val state = if (item?.state.toString() == "+" || item?.state.toString() == "!+") "+" else "-"
                val color = if(item?.state.toString().contains("!")) R.color.grey else if (state == "+") R.color.red else R.color.blue
                mContext?.let { ContextCompat.getColor(it, color)}?.let {
                    point_history_point_text.setTextColor(it)
                }
                val resultPointText = "${state}${Utils.addCommaP(item?.point.toString())}"
                point_history_point_text.text = resultPointText
                point_history_date_text.text = item?.date.toString()
                point_history_device_text.text = item?.device?.toString()
                point_history_cancel_text.setOnClickListener { onPointCancelClickListener?.onItemClickListener(itemView, adapterPosition) }
                point_history_swipe_layout.isSwipeEnabled = !item?.state.toString().contains("!")
                point_history_state_text.text = stateStr
            }
        }
    }
}