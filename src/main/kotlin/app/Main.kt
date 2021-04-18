package app
import io.javalin.Javalin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import org.eclipse.jetty.server.Authentication

const val JAVALIN_PORT = 7000
const val MONGO_PORT = 27017

// server
fun main() {


    val app = Javalin.create().start(JAVALIN_PORT)
    app.get("/") { ctx -> ctx.result("Hello World") }

    app.ws("/") { ws ->
        ws.onConnect { ctx -> println("Connected") }
        ws.onMessage { ctx ->
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
}
