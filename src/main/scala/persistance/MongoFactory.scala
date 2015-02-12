package main.scala.persistance
/**
* Created by fquintanilla on 16-01-15.}
 *
 * Factory of MongoClient objects, used to connect to a Mongo Database
*/
import com.mongodb.casbah.MongoClient
import net.liftweb.util.Props

object MongoFactory {
  val DBName : String = Props.mode match {
    case Props.RunModes.Test => "cryptoaudit-test"
    case _ => "cryptoaudit"
  }
  val mongoDB = MongoClient()(DBName)

}