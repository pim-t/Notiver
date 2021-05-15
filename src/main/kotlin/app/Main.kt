package app
import io.javalin.Javalin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.litote.kmongo.MongoOperator

const val JAVALIN_PORT = 7000
const val MONGO_PORT = 27017


@Serializable
data class SearchId (val id: String, val other_info: String)

fun main() {
    val app = Javalin.create().start(JAVALIN_PORT)
    app.get("/") { ctx -> ctx.result("Hello World") }

    app.ws("/") { ws ->
        var mongoClient: MongoClient? = null
        mongoClient = MongoClient("localhost", MONGO_PORT)
        ws.onConnect { ctx ->
            println("Connected")
        }

        ws.onMessage { ctx ->
            val message = ctx.message()
            print(message)
            val obj = Json.decodeFromString<SearchId>(message)
            print(obj)

            var db = mongoClient.getDB("Documents")
            var tbl = db.getCollection("doc_contents")
            val document = BasicDBObject()

            if (obj.id.equals("new_doc")) {
                var db = mongoClient.getDB("Documents")
                var tbl = db.getCollection("doc_contents")
                val document = BasicDBObject()
                document.put("other_info",obj.other_info)
                tbl.insert(document)
            }

            if (obj.id.equals("search")){
                print("searching")
                var document = BasicDBObject()
                document.put("title", "testing")
                var cursor = tbl.find(document)
                var it = cursor.iterator()
                while (it.hasNext()) {
                    ctx.send(it.next())
                }
            }
            if (obj.id.equals("update")) {
                print("update")
                var document = BasicDBObject()
                document.put("title", "testing")

                val newValDoc = BasicDBObject()
                newValDoc.put("title", "TESTING_2")

                val updateObject= BasicDBObject()
                updateObject.put("\$set", newValDoc)

                tbl.update(document, updateObject)
            }

        }
    }

}



