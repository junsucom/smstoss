package com.junsu.smstoss.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.junsu.smstoss.util.SharedPreferencesUtil


/**
 * Created by jslee on 2017-12-06.
 */
class ItemData(var title: String, var containLetter: String, var sendNumber: String) {

    companion object {
        @JvmStatic
        fun load(context: Context): MutableMap<String, ItemData> {
            var str: String = SharedPreferencesUtil.SETTING_DATAS.get(context);
            val type = object : TypeToken<MutableMap<String, ItemData>>() {}.type
            var settings: MutableMap<String, ItemData> = Gson().fromJson(str, type)
            return settings
        }

        @JvmStatic
        fun save(context: Context, datas: MutableMap<String, ItemData>) {
            var str: String = Gson().toJson(datas)
            SharedPreferencesUtil.SETTING_DATAS.set(context, str)
        }

        @JvmStatic
        fun add(context: Context, key:String, value: ItemData) {
            var datas: MutableMap<String, ItemData> = ItemData.load(context)
            datas.put(key, value)
            ItemData.save(context, datas)
        }

        @JvmStatic
        fun remove(context: Context, key: String) {
            var datas: MutableMap<String, ItemData> = ItemData.load(context)
            datas.remove(key)
            ItemData.save(context, datas)
        }

        @JvmStatic
        fun removeAll(context: Context) {
            ItemData.save(context, mutableMapOf<String, ItemData>())
        }
    }
}