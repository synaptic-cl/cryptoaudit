package main.scala.persistance

import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.{SalatMongoCursor, SalatDAO}
import main.scala.commitment.Commitment
import main.scala.persistance.MongoFactory
import org.bson.types.ObjectId
import com.novus.salat.global._

/**
 * Created by fquintanilla on 19-01-15.
 */
object CommitmentDAO extends SalatDAO[Commitment, ObjectId](collection = MongoFactory.mongoDB("Commitment")){

  /**
   * Finds a commitment in the database given its value (which should be unique for all commitments)
   * returns an Option with the found commitment or None if it wasn't found
   * */
  def findByCommitmentValue(value : String) : Option[Commitment] = {
    val list = this.find(MongoDBObject("value" -> value)).toList
    list.headOption
  }
}
