package main.scala.http

import dispatch._
import dispatch.Defaults._

/**
 * Created by fquintanilla on 09-01-15.
 */
trait Post {
  /**
   * Execute a HTTP Post Request on the given hostUrl and
   * with the parameters given in data.
   * Returns a Future[String] with the servers response
   * */
  def post(hostUrl : String, data: Map[String, String]): Future[String] = {
    val request = url(hostUrl)
    def postRequest = request << data
    val response : Future[String] = Http(postRequest OK as.String)
    return response
  }
}
