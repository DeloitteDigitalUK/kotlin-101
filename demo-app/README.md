Demo app
========

# Set up

1. Open IntelliJ IDEA
2. File > New > Project
3. Gradle > Kotlin (Java)
4. Next, etc.

# Configure

In your `build.gradle`, add this to your `dependencies`:

	dependencies {
	    compile 'io.vertx:vertx-web:3.5.1'
	}

# Create entrypoint

Under `src/main/kotlin`, create a new package, e.g. `com.example`

Create a new Kotlin file in this package, e.g. `Server`

# Create an HTTP server

Add a new function in your server file:

	fun startServer() {
		// ...
	}

Add the server code:

	val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)
    router.route().handler({ routingContext ->

        // This handler will be called for every request
        val response = routingContext.response()
        response.putHeader("content-type", "text/plain")

        // Write to the response and end it
        response.end("Hello World from Kotlin!")
    })

    server.requestHandler({ router.accept(it) }).listen(9090)
    println("Listening on http://localhost:9090")

> Make sure to add the imports (e.g. `io.vertx.ext.web.Router` etc.)

Run your application from IDEA.

If you hit http://localhost:9090 you will see the message returned.

# Structure our code

Add some packages:

	com.example.data
	com.example.model
	com.example.service

# Add models

New class in `com.example.model`:

	data class ToDoItem(val text: String,
	                    val modified: Date)

# Add business logic

## Data store

Add a fake database in `com.example.data`:

	class ToDoStore {
	    private val db = mutableListOf<ToDoItem>()

	    fun add(item: ToDoItem) {
	        db += item
	    }

	    fun list() = db
	}

## Service

Add service in `com.example.service`:

	class ToDoService {
	    private val store = ToDoStore()

	    fun add(text: String) {
	        val item = ToDoItem(
	                text = text,
	                modified = Date()
	        )

	        store.add(item)
	    }

	    fun list(): List<String> = store.list().map(this::formatForDisplay)

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

## Server

Add server endpoints, by replacing the existing `router.route().handler({` block with:

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

---

# Package for standalone distribution

In your `build.gradle`, we will:

Add this _immediately after_ the `buildscript` section:

	plugins {
	  id "com.github.johnrengelman.shadow" version "2.0.4"
	}

	apply plugin: 'application'

At the end of the `build.gradle` file, add:

	// this should be the FQN of your Server file, suffixed with 'Kt'
	mainClassName = 'com.example.ServerKt'

	shadowJar {
	    classifier = null
	    version = null
	}

Now let's build it:

	./gradlew clean shadowjar

You can run it with:

	java -jar build/libs/kotlin101.jar
