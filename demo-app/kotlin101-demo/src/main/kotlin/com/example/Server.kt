package com.example

import com.example.service.ToDoService
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

fun main(args: Array<String>) {
    startServer()
}

fun startServer() {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val toDoService = ToDoService()

    val router = Router.router(vertx)

    router.get("/add").handler { rc ->
        val text = rc.queryParam("text").firstOrNull()

        text?.let {
            println("Adding item: $text")
            toDoService.add(text)
            rc.response().setStatusCode(201).end()
        } ?: run {
            rc.response().setStatusCode(400).end("Missing text parameter")
        }
    }

    router.get("/items").handler { rc ->
        val items = toDoService.list()
        println("Listing ${items.size} items")

        val response = when (items.size) {
            0 -> "No items"
            else -> items.joinToString("\n")
        }
        rc.response().end(response)
    }

    router.get("/last").handler { rc ->
        println("Fetching last item added")
        rc.response().end(toDoService.last())
    }

    server.requestHandler({ router.accept(it) }).listen(9090)
    println("Listening on http://localhost:9090")
}
