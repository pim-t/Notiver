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
data class Project(val name: String, val language: String)

fun main() {
    val data = Project("kotlinx.serialization", "Kotlin")
    val string = Json.encodeToString(data)
    println(string) // {"name":"kotlinx.serialization","language":"Kotlin"}
    // Deserializing back into objects
    val obj = Json.decodeFromString<Project>(string)
    println(obj) // Project(name=kotlinx.serialization, language=Kotlin)
}


//    val app = Javalin.create().start(JAVALIN_PORT)
//    app.get("/") { ctx -> ctx.result("Hello World") }
//
//    app.ws("/") { ws ->
//        var mongoClient: MongoClient? = null
//        mongoClient = MongoClient("localhost", MONGO_PORT)
//        ws.onConnect { ctx -> println("Connected")
//
//
//        }
//        ws.onMessage { ctx ->
////                print(ctx.message())
//            val message = ctx.message()
//            print(message)
//            val obj = Json.decodeFromString<docId>(message)
//
//            print(obj)
//
//
//
//            var db = mongoClient.getDB("Documents")
//            var tbl = db.getCollection("doc_contents")
//            val document = BasicDBObject()
//            document.put("name",ctx.message())
//            tbl.insert(document)

//                if (data.id.equals("search")) {
//                    print("Searching")
//                    var db = mongoClient.getDB("Documents")
//                    var tbl = db.getCollection("doc_contents")
//
//                    var document = BasicDBObject()
//                    document.put("title", "testing")
//
//                    var cursor = tbl.find(document)
//                    var it = cursor.iterator()
//                    while (it.hasNext()) {
//                        ctx.send(it.next())
//                    }
////
//        }
//            if (data.id.equals("save")) {
//                print("hello")
//
//                var document = BasicDBObject()
//                document.put("title", "testing")
//
//                val newValDoc = BasicDBObject()
//                newValDoc.put("title", "TESTING_2")
//
//                val updateObject= BasicDBObject()
//                updateObject.put("\$set", newValDoc)
//
//                tbl.update(document, updateObject)
//
//            }
            //        else {
//                    var db = mongoClient.getDB("Documents")
//                    var tbl = db.getCollection("doc_contents")
//                    val document = BasicDBObject()
//                    document.put("name",ctx.message())
//                    tbl.insert(document)
//                }



//
//        }
//    }
//}


data class docId (
    val id: String
        )