package app
import io.javalin.Javalin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient

const val JAVALIN_PORT = 7070
const val MONGO_PORT = 27017

// server
fun main() {

    Javalin.create {
        it.addStaticFiles("/public")
    }.apply {
        ws("/info") { ws ->
            ws.onConnect { ctx ->
                println("Connected")
            }
            ws.onMessage{ ctx ->
                print(ctx.message())
                var mongoClient: MongoClient? = null
                mongoClient = MongoClient("localhost", MONGO_PORT)

                var db = mongoClient.getDB("testDB")
                var tbl = db.getCollection("user")
                val document = BasicDBObject()
                document.put("name",ctx.message())
                tbl.insert(document)

            }
        }
    }.start(JAVALIN_PORT)

}

