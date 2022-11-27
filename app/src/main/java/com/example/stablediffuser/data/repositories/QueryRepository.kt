package com.example.stablediffuser.data.repositories

import android.content.SharedPreferences

private const val KEY_SHARED_PREF_QUERIES = "stable_diffuser_queries"

private const val MAX_COUNT_QUERY = 5

private const val DEFAULT_QUERY_EXAMPLE = "Monkey in a pink spacesuit, in the style of Banksy"

class QueryRepository(
    private val sharedPref: SharedPreferences
) {
    private var internalQueries: MutableList<String> = mutableListOf()

    fun add(query: String) = synchronized(internalQueries) {
        if (!internalQueries.contains(query)) {
            internalQueries.add(query)
            if (internalQueries.size > MAX_COUNT_QUERY) {
                internalQueries.removeAt(0)
            }
        }
    }

    fun getAll(): List<String> = synchronized(internalQueries) {
        internalQueries.reversed()
    }

    fun save() {
        with(sharedPref.edit()) {
            putStringSet(KEY_SHARED_PREF_QUERIES, internalQueries.toSet())
            apply()
        }
    }

    fun restore() {
        val querySet = sharedPref.getStringSet(KEY_SHARED_PREF_QUERIES, null)
        internalQueries = querySet?.toMutableList() ?: mutableListOf(DEFAULT_QUERY_EXAMPLE)
    }
}