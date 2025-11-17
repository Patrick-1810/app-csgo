package com.example.appcsgo.data.repository

import android.content.Context
import com.example.appcsgo.data.model.QuickAccessItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuickAccessRepository private constructor(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getQuickAccessItems(): List<QuickAccessItem> {
        val json = prefs.getString(KEY_ITEMS, null) ?: return emptyList()
        val type = object : TypeToken<List<QuickAccessItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addQuickAccessItem(item: QuickAccessItem) {
        val current = getQuickAccessItems().toMutableList()

        current.removeAll { it.id == item.id && it.type == item.type }

        current.add(0, item)

        val maxSize = 6
        if (current.size > maxSize) {
            current.subList(maxSize, current.size).clear()
        }

        val json = gson.toJson(current)
        prefs.edit().putString(KEY_ITEMS, json).apply()
    }

    companion object {
        private const val PREFS_NAME = "quick_access_prefs"
        private const val KEY_ITEMS = "quick_access_items"

        @Volatile
        private var INSTANCE: QuickAccessRepository? = null

        fun getInstance(context: Context): QuickAccessRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: QuickAccessRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
