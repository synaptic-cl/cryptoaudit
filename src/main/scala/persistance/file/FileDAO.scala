package main.scala.persistance.file

import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._
import main.scala.persistance.MongoFactory
import main.scala.persistance.transaction.{TransactionDAO, Transaction}
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 28-01-15.
 *
 * Data Access Object for the File Mongo collection
 * it mediates all database operations for File objects

 */
object FileDAO extends SalatDAO[File, ObjectId](collection = MongoFactory.mongoDB("File")){

  /**
   * Set the given transaction to the given file
   * if the file already existed int the database it is modified
   * otherwise its created in the database with the given transaction
   * */
  def setTransaction(file : File, tx : Transaction) : Boolean = {
    //First check if the given Transaction is in the database
    val txFromDB = TransactionDAO.findOneById(tx._id)
    if (txFromDB == None) return false
    file.tx_id = Some(tx._id)
    try{
      this.save(file)
    } catch{
      case e : Exception => false
    }
    true
  }

  /**
   * Returns an Option with a Transaction if the given file was associated
   * with one in the database, otherwise return None
   * */
  def getTransaction(file : File) : Option[Transaction] = {
    if (file.tx_id == None) return None
    TransactionDAO.findOneById(file.tx_id.get)
  }
}
