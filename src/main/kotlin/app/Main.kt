package app
import io.javalin.Javalin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

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


        }
        ws.onMessage { ctx ->
                print(ctx.message())
            val mapper = jacksonObjectMapper()
            val data = mapper.readValue<docId>(ctx.message())

                if (data.id.equals("search")) {

                    var db = mongoClient.getDB("Documents")
                    var tbl = db.getCollection("doc_contents")
                    var document = BasicDBObject()
                    document.put("title", "testing")

                    var cursor = tbl.find(document)
                    var it = cursor.iterator()
                    while (it.hasNext()) {
                        ctx.send(it.next())
                    }
//                    ctx.send("""{"name":"test name", "age":25}""")
        }
        //        else {
//                    var db = mongoClient.getDB("Documents")
//                    var tbl = db.getCollection("doc_contents")
//                    val document = BasicDBObject()
//                    document.put("name",ctx.message())
//                    tbl.insert(document)
//                }




        }
    }
}


data class docId (
    val id: String
        )