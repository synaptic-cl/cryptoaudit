package main.scala.persistance
/**
* Created by fquintanilla on 16-01-15.
*/
import com.mongodb.casbah.MongoClient

object MongoFactory {
  val mongoDB = MongoClient()("cryptoaudit")
}