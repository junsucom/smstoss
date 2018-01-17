package com.junsu.smstoss.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "id")
                val id: Long = 0,
                @ColumnInfo(name = "itemtitle")
                val itemTitle: String,
                @ColumnInfo(name = "receivenumber")
                val receiveNumber: String,
                @ColumnInfo(name = "sendnumber")
                val sendNumber: String,
                @ColumnInfo(name = "enabled")
                val enabled: Boolean = true,
                @ColumnInfo(name = "date")
                var date: Long = Date().time)