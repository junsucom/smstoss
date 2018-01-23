package com.junsu.smstoss.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.junsu.smstoss.R
import com.junsu.smstoss.persistence.Item

class ItemViewHolder(parent :ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)) {
    private val titleView = itemView.findViewById<TextView>(R.id.view_item_title)
    private val receiverView = itemView.findViewById<TextView>(R.id.view_item_receiver)
    private val senderView = itemView.findViewById<TextView>(R.id.view_item_sender)
    val switchView = itemView.findViewById<Switch>(R.id.view_item_switch)
    var item: Item? = null

    fun bindTo(item: Item?) {
        this.item = item
        titleView.text = item?.title
        receiverView.text = item?.receiveNumber
        senderView.text = item?.sendNumber
        switchView.isChecked = item?.enabled!!
    }
}