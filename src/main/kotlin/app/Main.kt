package app
import io.javalin.Javalin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import org.bson.Document
import org.eclipse.jetty.server.Authentication
import org.json.JSONObject
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.jsonSchema

const val JAVALIN_PORT = 7000
const val MONGO_PORT = 27017

// server
fun main() {


    val app = Javalin.create().start(JAVALIN_PORT)
    app.get("/") { ctx -> ctx.result("Hello World") }

    app.ws("/") { ws ->
        var mongoClient: MongoClient? = null
        mongoClient = MongoClient("localhost", MONGO_PORT)
        ws.onConnect { ctx -> println("Connected")
            var db = mongoClient.getDB("Documents")
            var tbl = db.getCollection("doc_contents")
            var document = BasicDBObject()
            document.put("title", "testing")

            var cursor = tbl.find(document)
            var it = cursor.iterator()
            while (it.hasNext()) {
                print(it.next())
            }
            ctx.send("""{"name":"test name", "age":25}""")
        }
        ws.onMessage { ctx ->
                print(ctx.message())

                var db = mongoClient.getDB("Documents")
                var tbl = db.getCollection("doc_contents")
                val document = BasicDBObject()
                document.put("name",ctx.message())
                tbl.insert(document)


        }
    }
}
