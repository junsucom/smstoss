package com.junsu.smstoss.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.persistence.ItemDatabase
import com.junsu.smstoss.util.ioThread

/**
 * A simple ViewModel that provides a paged list of delicious Cheeses.
 */
class ItemViewModel(app: Application) : AndroidViewModel(app) {
    val dao = ItemDatabase.get(app).itemDao()

    val allItems = dao.allItems().create(0,
            PagedList.Config.Builder()
                    .setPageSize(PAGE_SIZE)
                    .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                    .build())!!

    fun insert(title: CharSequence, rn: CharSequence, sn: CharSequence) = ioThread {
        dao.insertItem(Item(itemTitle = title.toString(), receiveNumber = rn.toString(), sendNumber = sn.toString()))
    }

    fun remove(item: Item) = ioThread {
        dao.deleteItem(item)
    }

    fun enable(id:Long, enable: Boolean) = ioThread {
        dao.enable(id, enable)
    }

    companion object {
        private const val PAGE_SIZE = 30

        private const val ENABLE_PLACEHOLDERS = true
    }
}