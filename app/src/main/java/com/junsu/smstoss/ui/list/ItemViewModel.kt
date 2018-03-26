package com.junsu.smstoss.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.LivePagedListBuilder
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.persistence.ItemDatabase
import com.junsu.smstoss.util.SingleLiveEvent
import com.junsu.smstoss.util.ioThread

class ItemViewModel(app: Application) : AndroidViewModel(app) {
    val dao = ItemDatabase.get(app).itemDao()
    internal val newItemEvent = SingleLiveEvent<Void>()

    val allItems = LivePagedListBuilder<Int, Item>(
            dao.allItems(), /* page size */ PAGE_SIZE).build();

    fun remove(item: Item) = ioThread {
        dao.deleteItem(item)
    }

    fun enable(id:Long, enable: Boolean) = ioThread {
        dao.enable(id, enable)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    fun addNewItem() {
        newItemEvent.call()
    }
}