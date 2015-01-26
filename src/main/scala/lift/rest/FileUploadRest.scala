package main.scala.lift.rest

import net.liftweb.common.{Box, Full}
import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper
import net.liftweb.json._

/**
 * Created by fquintanilla on 26-01-15.
 */
object FileUploadRest extends RestHelper{

  case class FileUploadResponse(val url : String)
  /**
   * Serve the specified URL
   *
   * e.g. case "hello" :: "world" :: Nil Get _ => ...
   * will respond to a GET request on the http://<host>/hello/world/ URL
   * */
  serve{
    // /api/file/
    case "api" :: "file" :: Nil Post _ =>
      for {
        content  <- S.param("file") ?~ "Param 'file' missing"
        response <- processFile(content) ?~ "The file couldn't be processed"
      }yield Extraction.decompose(response)
  }

  private def processFile(file : String) : Box[FileUploadResponse] = {
    Full(FileUploadResponse("a"))
  }

}
