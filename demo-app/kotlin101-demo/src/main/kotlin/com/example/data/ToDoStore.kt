package com.example.data

import com.example.model.ToDoItem

class ToDoStore {
    private val db = mutableListOf<ToDoItem>()

    fun add(item: ToDoItem) {
        db += item
    }

    fun list() = db
}
