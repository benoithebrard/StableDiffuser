package com.example.stablediffuser.data.repositories

import android.content.SharedPreferences

private const val KEY_SHARED_PREF_QUERY = "stable_diffuser_query"

private const val KEY_SHARED_PREF_QUERY_COUNT = "stable_diffuser_query_count"

private const val MAX_COUNT_QUERY = 5

private const val DEFAULT_QUERY_EXAMPLE = "Monkey in a pink spacesuit, in the style of Banksy"

class QueryRepository(
    private val sharedPref: SharedPreferences
) {
    private var orderedQueries: MutableList<String> = mutableListOf()

    fun add(query: String) {
        if (!orderedQueries.contains(query)) {
            orderedQueries.add(query)
            if (orderedQueries.size > MAX_COUNT_QUERY) {
                orderedQueries.removeAt(0)
            }
        }
    }

    fun getAll(): List<String> = orderedQueries.reversed()

    fun clearAll() = orderedQueries.clear()

    fun save() {
        with(sharedPref.edit()) {
            repeat((0 until orderedQueries.size).count()) { index ->
                putString("${KEY_SHARED_PREF_QUERY}_$index", orderedQueries[index])
            }
            putInt(KEY_SHARED_PREF_QUERY_COUNT, orderedQueries.size)
            apply()
        }
    }

    fun restore() {
        orderedQueries.clear()

        with(sharedPref) {
            val queryCount = getInt(KEY_SHARED_PREF_QUERY_COUNT, 0)
            repeat((0 until queryCount).count()) { index ->
                getString("${KEY_SHARED_PREF_QUERY}_$index", null)?.also { query ->
                    orderedQueries.add(query)
                }
            }
        }

        if (orderedQueries.isEmpty()) {
            orderedQueries.add(DEFAULT_QUERY_EXAMPLE)
        }
    }
}