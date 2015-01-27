package main.scala.lift.rest

import main.scala.commitment.Commitment
import main.scala.persistance.TransactionDAO
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
  case class StatusResponse(val tx_id:String, val commitment:Commitment)

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
  private def getStatus(in : String) : Box[StatusResponse] = {
    if (!ObjectId.isValid(in)) return Empty
    val id = new ObjectId(in)
    val tx = TransactionDAO.findOneById(id)
    if (tx == None) return Empty
    Full(new StatusResponse(tx.get.transaction, tx.get.commitment))
  }



}
