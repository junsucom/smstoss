package com.junsu.smstoss.persistence

import android.arch.paging.LivePagedListProvider
import android.arch.persistence.room.*

import io.reactivex.Flowable

/**
 * Data Access Object for the users table.
 */
@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun allItems(): LivePagedListProvider<Int, Item>

    /**
     * Get a user by id.

     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Long): Flowable<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("UPDATE items SET enabled = :enable WHERE id = :id")
    fun enable(id: Long, enable: Boolean)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM items WHERE receivenumber = :receiverNumber")
    fun findNumber(receiverNumber: String): List<Item>

    @Query("DELETE FROM items")
    fun deleteAllItems()
}
