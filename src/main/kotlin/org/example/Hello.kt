package org.example
import io.javalin.Javalin

fun main(args: Array<String>) {
    println("Hello, World")
    val app = Javalin.create().start(7000)
    app.get("/") {ctx -> ctx.result("Hello World")}
}

