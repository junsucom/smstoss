package com.junsu.smstoss.ui.edit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.persistence.ItemDatabase
import com.junsu.smstoss.util.ioThread

/**
 * A simple ViewModel that provides a paged list of delicious Cheeses.
 */
class EditViewModel(app: Application) : AndroidViewModel(app) {
    val dao = ItemDatabase.get(app).itemDao()

    fun read(id:Long) = dao.getItemById(id)

    fun insert(title: CharSequence, rn: CharSequence, sn: CharSequence) = ioThread {
        dao.insertItem(Item(itemTitle = title.toString(), receiveNumber = rn.toString(), sendNumber = sn.toString()))
    }

    fun modify(id:Long, title: CharSequence, rn: CharSequence, sn: CharSequence) = ioThread {
        dao.insertItem(Item(id = id, itemTitle = title.toString(), receiveNumber = rn.toString(), sendNumber = sn.toString()))
    }

    companion object {
        private const val PAGE_SIZE = 30

        private const val ENABLE_PLACEHOLDERS = true
    }
}