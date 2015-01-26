package main.scala.lift.rest

import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper
import net.liftweb.json._
import net.liftweb.json.JsonParser._

/**
 * Created by fquintanilla on 26-01-15.
 */
object FileUploadRest extends RestHelper{

  case class TestResponse(val si : String)
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
        fileContent : String <- S.param("file") ?~ "Param 'file' missing"
      }yield Extraction.decompose(new TestResponse(fileContent))
  }

}
