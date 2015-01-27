package main.scala.lift.rest

import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.Extraction

/**
 * Created by fquintanilla on 26-01-15.
 */
object FileStatusRest extends RestHelper{

  /**
   * case class to represent the status of a file in the system
   * */
  case class Status(val id : String)

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
  private def getStatus(in : String) : Box[Status] = {

    if (in.compareTo("10") < 0)
      return Full(new Status(in))
    Empty
  }

}
