package com.junsu.smstoss

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.persistence.ItemDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SmsReceiverTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    companion object {
        val TAG = "TEST"
        val testTitle = "testTitle"
        val receiveNumber = "1234"
        val sendNumber = "5678"
    }

    private lateinit var database: ItemDatabase

    var testItemId: Long = 0

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ItemDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun findNumber() {
        var testItem = Item(title = testTitle, receiveNumber = receiveNumber, sendNumber = sendNumber)
        testItemId = database.itemDao().insertItem(testItem)

        database.itemDao().findNumber(receiveNumber).test()
                .assertValueCount(1)
                .assertValue({ it.get(0).id === testItemId })
    }


}
