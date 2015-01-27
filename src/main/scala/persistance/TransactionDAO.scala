package main.scala.persistance

import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 19-01-15.
 */
object TransactionDAO extends SalatDAO[Transaction, ObjectId](collection = MongoFactory.mongoDB("Transaction")){

  /**
   * Finds a transaction in the database given its commitment's value (which should be unique for all commitments)
   * returns an Option with the found transaction or None if it wasn't found
   * */
  def findByCommitmentValue(value : String) : Option[Transaction] = {
    val list = this.find(MongoDBObject("commitment.value" -> value)).toList
    list.headOption
  }
  /**
   * Returns an Option with the ObjectId corresponding to the transaction with the given
   * commitment's value or None if no commitment with that value could be found
   * */
  def getIdFromValue(value : String) : Option[ObjectId] = {
    val comm = findByCommitmentValue(value)
    if (comm == None) return None
    Some(comm.get._id)
  }
}
