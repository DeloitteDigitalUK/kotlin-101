Concepts
========

# Origin and history

- Dates back to Java 6 (late 00's)
- Started by JetBrains (great tooling)
- Experimental language concepts (c.f. Traits)
- Considered stable (1.0) since mid 10's
- Initially targetted the JVM
  - JavaScript
  - Native (via LLVM)
  - WebAssembly
- Always prioritised interop (JVM, JS etc.)
- Endorsed by Google at I/O 2017 (Android)

# Goals

- Productivity!
- First class interop
  - e.g. with the JVM ecosystem (build tools, IDEs, packaging)
- Safety
  - Favours immutability
  - Null-safe (c.f. The Billion Dollar Mistake)
- Remove the boilerplate! Terse, but readable syntax
- Choose functional or imperative styles, depending on the job at hand
- Strongly typed & type inference
- Practicality over purity

# Syntax

## Variables

Like many popular modern languages in the last 5-10 years, it favours the post symbolic declaration style (c.f. Swift, Golang, TypeScript).

    val foo: String = "Hello world!"

> Note: no semicolon

Type inference at work - don't need to tell the compiler something obvious:

    val foo = "Hello world!"

> Note: the type of `foo` can be inferred to be `String`

### Mutability

You should prefer immutability wherever possible to avoid ambiguity and remove hard to find bugs due to unintended side effects.

An immutable variable:

    val foo = "Hello world!"
    
    // fails to compile
    foo = "something else"

Occasionally you still want mutable variables:

    var foo = "Ada Lovelace"
    foo = "Grace Hopper"

> Rule of thumb: challenge yourself to find a way to make your state immutable

## Null safe

The concept of null safety is built right into the type system. You cannot accidentally* assign a nullable variable to one requiring a non-null value.

> * though you can override the compiler if you think you know best

Here's a non-null variable:

    val bar: Int = 42

You can always trust it when you dereference it not to blow up with the dreaded `NullPointerException`.

    // always works
    println(

    // enforced at compile time
    val qux: Int = bar

### Collections and ranges

Powerful built in collection types, e.g.

    val foo = listOf("The", "quick", "brown", "fox")

## Functions

Functions are declared with `fun`:

    fun add(first: Int, second: Int) {
        return first + second
    }

### Function expressions

You can declare short functions as expressions, e.g.

    fun add(first: Int, second: Int) = first + second
    
> Note: no `return`, no type declaration required

This encourages a functional style, e.g.

    fun power(operand: Int) = operand * operand
    
    [1..10].map(this::power).forEach(

### Extension functions

You can extend classes that are built in, or where you otherwise can't modify the source code.

    

### Top level functions

Functions can be declared at the top level, i.e. within a file without any enclosing type.

This also goes for your program entrypoint:

    package com.example

    fun main(args: Array<String>) {
        println "Hello world!"
    }

## Classes

Java-like syntax:

    class ToDoItem {
        fun add(first: Int, second: Int) {
          
        }
    }

### Properties

Kotlin, like C#, has proper properties. They act like getters/setters (and from Java they have get/set methods), to reduce the ceremony around custom models.

### Default modifiers and visibility

Members (functions, properties etc.) are public by default. You can of course use the following modifiers:

- private
- protected
- public
- internal (within the scope of the current module)

Members are _final_ by default, although you can override this with the `open` or `abstract` modifiers. You can also turn this off at a module level with a compiler setting.

### Constructors

...

### Data classes

Bye bye boilerplate.

## Misc

## String templates

## Inlining

## Infix

## Operator overloading

## Pairs, multiple return types and destructuring
