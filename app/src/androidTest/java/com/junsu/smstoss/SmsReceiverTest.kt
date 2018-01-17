package com.junsu.smstoss

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.runner.AndroidJUnit4
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.persistence.ItemDatabase
import org.hamcrest.Matchers.hasItems
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SmsReceiverTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        val testTitle = "testTitle"
        val testItem = Item(itemTitle = testTitle, receiveNumber = "1234", sendNumber = "5678")
        ItemDatabase.get(appContext).itemDao().insertItem(testItem)

        val itemList = ItemDatabase.get(appContext).itemDao().findNumber("1234")


        assertThat(itemList, hasItems(testItem))
    }

}
