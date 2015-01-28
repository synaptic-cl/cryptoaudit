package main.scala.persistance.file

import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._
import main.scala.persistance.MongoFactory
import main.scala.persistance.transaction.Transaction
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 28-01-15.
 */
object FileDAO extends SalatDAO[File, ObjectId](collection = MongoFactory.mongoDB("File")){

  def setTransaction(file : File, tx : Transaction) : Boolean = {
    file.tx_id = Some(tx._id)
    try{
      this.save(file)
    } catch{
      case e : Exception => false
    }
    true
  }
}
