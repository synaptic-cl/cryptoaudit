package main.scala.http

import scalaj.http._

/**
 * Created by philippe on 26-01-15.
 */

trait Get {
  /**
   * Execute a HTTP Get Request on the given hostUrl and
   * with the parameters given in data.
   * Returns a Future[String] with the servers response
   **/
  def get(hostUrl: String, data: Map[String, String]): String = {


    val response = Http(hostUrl).params(data)
    
//
//    for ((key, value) <- data) {
//      myGet.addQueryParameter(key, value)
//    }

    return response.asString.body

  }

}

