package com.junsu.smstoss.ui.list

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.junsu.smstoss.persistence.Item

class ItemsAdapter constructor(private val clickListener: View.OnClickListener,
                               private val toggleListener: CompoundButton.OnCheckedChangeListener) : PagedListAdapter<Item, ItemViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(getItem(position))
        val item = getItem(position)

        //아이템 클릭 리스너
        with(holder.itemView) {
            tag = item
            setOnClickListener(clickListener)
        }

        //switch 버튼 리스너
        with(holder.switchView) {
            tag = item
            setOnCheckedChangeListener(toggleListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
            ItemViewHolder(parent)


    companion object {
        private val diffCallback = object : DiffCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.id == newItem.id
                            && oldItem.title.equals(newItem.title)
                            && oldItem.receiveNumber.equals(newItem.receiveNumber)
                            && oldItem.sendNumber.equals(newItem.id)
        }
    }
}