package com.movile.summit.spark

import com.google.gson.Gson
import com.movile.summit.spark.model.User
import org.koin.standalone.KoinComponent
import spark.Spark.get
import spark.Spark.post
import spark.kotlin.port
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

class MainApplication : KoinComponent {

    var users: ConcurrentHashMap<String, User> = ConcurrentHashMap()

    fun run() {
        port(8080)

        post("/users") { request, _ ->

            val user = Gson().fromJson<User>(request.body(), User::class.java)
            val userId = UUID.randomUUID().toString()
            users[userId] = user
            userId
        }

        get("/users/:id") { request, response ->
            response.type("application/json")

            val user = users.getOrDefault(request.params(":id"), User("not found", "not_found", "not_found"))
            Gson().toJson(user)
        }
    }
}

fun main() {
    MainApplication().run()
}





