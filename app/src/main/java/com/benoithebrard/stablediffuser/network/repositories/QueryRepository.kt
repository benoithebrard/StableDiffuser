package com.benoithebrard.stablediffuser.network.repositories

import android.content.SharedPreferences
import com.benoithebrard.stablediffuser.utils.PromptGenerator
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_SHARED_PREF_QUERY = "stable_diffuser_query"

private const val KEY_SHARED_PREF_QUERY_COUNT = "stable_diffuser_query_count"

private const val MAX_COUNT_QUERY = 5

@Singleton
class QueryRepository @Inject constructor(
    private val sharedPref: SharedPreferences
) {
    private var orderedPrompts: MutableList<String> = mutableListOf()

    fun add(query: String) {
        if (!orderedPrompts.contains(query)) {
            orderedPrompts.add(query)
            if (orderedPrompts.size > MAX_COUNT_QUERY) {
                orderedPrompts.removeAt(0)
            }
        }
    }

    fun getAll(): List<String> = orderedPrompts.reversed()

    fun clearAll() {
        orderedPrompts.clear()
        orderedPrompts.add(PromptGenerator.randomQuery())
    }

    fun save() {
        with(sharedPref.edit()) {
            repeat((0 until orderedPrompts.size).count()) { index ->
                putString("${KEY_SHARED_PREF_QUERY}_$index", orderedPrompts[index])
            }
            putInt(KEY_SHARED_PREF_QUERY_COUNT, orderedPrompts.size)
            apply()
        }
    }

    fun restore() {
        orderedPrompts.clear()

        with(sharedPref) {
            val queryCount = getInt(KEY_SHARED_PREF_QUERY_COUNT, 0)
            repeat((0 until queryCount).count()) { index ->
                getString("${KEY_SHARED_PREF_QUERY}_$index", null)?.also { query ->
                    orderedPrompts.add(query)
                }
            }
        }

        if (orderedPrompts.isEmpty()) {
            orderedPrompts.add(PromptGenerator.randomQuery())
        }
    }
}