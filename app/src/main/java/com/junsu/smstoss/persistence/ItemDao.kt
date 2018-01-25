package com.junsu.smstoss.persistence

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun allItems(): DataSource.Factory<Int, Item>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Long): Flowable<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item): Long

    @Query("UPDATE items SET enabled = :enable WHERE id = :id")
    fun enable(id: Long, enable: Boolean)

    @Delete
    fun deleteItem(item: Item)

    @Query("DELETE FROM items WHERE id = :id")
    fun deleteItemId(id: Long)

    @Query("SELECT * FROM items WHERE receiverNumber = :receiverNumber")
    fun findNumber(receiverNumber: String): Flowable<List<Item>>

//    @Query("SELECT * FROM items")
//    fun findAll(): List<Item>

//    @Query("DELETE FROM items")
//    fun deleteAllItems()
}
