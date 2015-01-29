package main.scala.lift.rest

import main.scala.commitment.Commitment
import main.scala.persistance.file.FileDAO
import main.scala.persistance.transaction.TransactionDAO
import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.Extraction
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 26-01-15.
 */
object FileStatusRest extends RestHelper{

  /**
   * case class to represent the status of a file in the system
   * */
  case class StatusResponse(val tx_id:String, val status: String, val filename:String)

  serve {
    case "api" :: "file" :: "status" :: fileId :: Nil Get _ =>
      for {
        resp <- getStatus(fileId) ?~ "The status couldn't be retrieved for the given id"
      } yield Extraction.decompose(resp)
  }

  /**
   * Method to check the status of a file upload given a fileId
   * returns a Box with an object representing the status or Empty if
   * the fileId matched no file in the system
   * */
  private def getStatus(fileId : String) : Box[StatusResponse] = {
    //if fileId is not a valid ObjectId the constructor will throw an exception
    if (!ObjectId.isValid(fileId)) return Empty
    val id = new ObjectId(fileId)
    val file = FileDAO.findOneById(id)
    if (file == None) return Empty
    val tx = FileDAO.getTransaction(file.get)
    val tx_hash = if (tx == None) "" else tx.get.transaction
    Full(new StatusResponse(tx_hash, "pending", file.get.filename))
  }



}
