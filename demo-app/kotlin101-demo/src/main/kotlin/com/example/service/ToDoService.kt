package com.example.service

import com.example.data.ToDoStore
import com.example.model.ToDoItem
import java.util.*

class ToDoService {
    private val store = ToDoStore()

    fun add(text: String) {
        val item = ToDoItem(
                text = text,
                modified = Date()
        )

        store.add(item)
    }

    fun list(): List<String> = store.list()
            .map(this::formatForDisplay)

    fun last(): String {
        val sorted = store.list()
                .sortedByDescending { it.modified }
                .firstOrNull()

        return sorted?.let { formatForDisplay(it) } ?: "No items"
    }

    private fun formatForDisplay(item: ToDoItem): String {
        return "${item.modified} - ${item.text}"
    }
}
