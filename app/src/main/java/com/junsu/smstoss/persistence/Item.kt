package com.junsu.smstoss.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "id")
                val id: Long = 0,
                @ColumnInfo(name = "title")
                val title: String,
                @ColumnInfo(name = "receiverNumber")
                val receiveNumber: String,
                @ColumnInfo(name = "sendNumber")
                val sendNumber: String,
                @ColumnInfo(name = "enabled")
                val enabled: Boolean = true,
                @ColumnInfo(name = "date")
                var date: Long = Date().time)