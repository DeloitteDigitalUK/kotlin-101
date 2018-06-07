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

The concept of null safety is built right into the type system. You cannot _accidentally_ assign a nullable variable to one requiring a non-null value.

> Note: you can override the compiler if you think you know best, using the `!!` operator.

Here's a non-null variable:

    val bar: Int = 42

You can always trust it when you dereference it not to blow up with the dreaded `NullPointerException`.

    // always works
    bar.something()

    // enforced at compile time
    val qux: Int = bar

If a variable can be `null` then you declare it with the `?` suffix on its type, e.g.

    Int?
    String?

You can test for null in the usual way:

    var maybe: String?
    if (null != maybe) {
        println(maybe.length)
    }

...or you can utilise Kotlin's null-safe operator:

    // this is always safe
    println(maybe?.length)

### Elvis operator

There is a shorthand for the case where an expression evaluates to `null`, using the Elvis operator `?:`

    val result = maybe ?: "Something else"

This is semantically equivalent to the `.orElse()` method in Java 8's `Optional` class.

### Collections and ranges

Powerful built in collection types, e.g.

    val foo = listOf("The", "quick", "brown", "fox")
    val bar = mapOf(
        "key1" to "value1",
        "key2" to "value2"
        // etc.
    )

> Note: There are no 'collection literals' in Kotlin, unlike JavaScript or Groovy.

## Functions

Functions are declared with `fun`:

    fun add(first: Int, second: Int): Int {
        return first + second
    }

### Function expressions

You can declare short functions as expressions, e.g.

    fun add(first: Int, second: Int) = first + second
    
> Note: no `return`, no return type declaration required

This encourages a functional style, e.g.

    fun power(operand: Int) = operand * operand
    
    [1..10].map(this::power).forEach(println)

### Extension functions

You can extend classes that are built in, or where you otherwise can't modify the source code.

    val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH)

    fun LocalDateTime.toDisplayForm() {
        // the implicit 'this' is the instance on which the extension method is invoked
        return this.format(formatter)
    }

You can use this function on any `LocalDateTime` in scope:

    val now = LocalDateTime.now();
    println(now.toDisplayForm())

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
          // etc...
        }
    }

Constructors are declared with the type name:

    class ToDoItem(private val someValue: String) {

        fun work() {
          // you can work with the 'someValue' property
          println(someValue)
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

### Delegation and 'lazy'

Kotlin supports the concept of delegation for properties.

A good example of this is the built-in `lazy` delegate, used like this:

    val formatter by lazy { DateTimeFormatter.ofPattern("dd.mm.yyyy", Locale.ENGLISH) }

This means that `formatter` is not initialised until you use it, and subsequently it is memoised (i.e. cached), for efficiency.

### Data classes

Bye bye boilerplate!

In Kotlin:

    data class Person(val name: String, val age: Int)

In Java:

    public class Person {
       private String name;
       private int age = 0;

       public Person(String name, int age) {
           this.name = name;
           this.age = age;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public int getAge() {
           return age;
       }

       public void setAge(int age) {
           this.age = age;
       }

       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (o == null || getClass() != o.getClass()) return false;

           Person person = (Person) o;

           if (name != null ? !name.equals(person.name) : person.name != null) return false;
           if (age != 0 ? age != person.age : person.age != 0) return false;
       }

       @Override
       public int hashCode() {
           int result = name != null ? name.hashCode() : 0;
           result = 31 * result + age;
           return result;
       }

       @Override
       public String toString() {
           return "Person{" +
                   "name='" + name + '\'' +
                   ", age='" + age + '\'' +
                   '}';
       }
  }

## Misc

* String templates
* The `when` block - like `switch` but better (checking types)
* Using `when` and `if` as expressions
* Smart cast
* Pairs, multiple return types and destructuring
* Inlining
* Infix
* Operator overloading
