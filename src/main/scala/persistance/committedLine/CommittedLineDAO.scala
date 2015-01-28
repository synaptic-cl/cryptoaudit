package main.scala.persistance.committedLine

import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.{SalatDAO, SalatMongoCursor}
import com.novus.salat.global._
import main.scala.persistance.MongoFactory
import main.scala.persistance.transaction.{Transaction, TransactionDAO}
import org.bson.types.ObjectId

/**
* Created by fquintanilla on 19-01-15.
*/
object CommittedLineDAO extends SalatDAO[CommittedLine, ObjectId](collection = MongoFactory.mongoDB("CommittedLine")){

  override def insert(toPersist : CommittedLine) : Option[ObjectId] = {
    if (validate(toPersist)){
      super.insert(toPersist)
    }
    else{
      None
    }
  }
  /**
   * Method used to validate a committedLine to be persisted
   * returns true if toPersist is a valid CommittedLine and false otherwise
   * */
  private def validate(toPersist : CommittedLine) : Boolean = {

    /* A valid CommittedLine should have no empty fields and
    * reference a valid transaction from the database */
    val transactionId = toPersist.transaction
    val transaction = TransactionDAO.findOneById(transactionId)
    if (transaction == None) return false
    true
  }

  def findByLine(line : String) : SalatMongoCursor[CommittedLine] = {
    this.find(MongoDBObject("line" -> line))
  }

  def findByTransactionId(id : ObjectId) : SalatMongoCursor[CommittedLine] = {
    this.find(MongoDBObject("transaction" -> id))
  }

  def findByTransaction(trans : Transaction) : SalatMongoCursor[CommittedLine] = {
    val commId = trans._id
    this.findByTransactionId(commId)
  }

  def findTransactionFromLine(line : CommittedLine) : Option[Transaction] = {
    val transactionId = line.transaction
    TransactionDAO.findOneById(transactionId)
  }

  def findTransactionFromLineId(lineId : ObjectId) : Option[Transaction] = {
    val line = this.findOneById(lineId)
    if (line == None) return None
    this.findTransactionFromLine(line.get)
  }


}
