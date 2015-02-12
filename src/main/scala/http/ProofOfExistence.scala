package main.scala.http

import dispatch._
import dispatch.Defaults._

/**
 * Created by fquintanilla on 09-01-15.
 *
 * Class with the methods used to connect with the ProofOfExistence.com API
 * http://proofofexistence.com/developers
 *
 * Extends Post and encapsulates calls to a REST API as methods
 */

object ProofOfExistence extends Post{
  /**
   * Execute a call to the ProofOfExistence register API
   * to register the given String, which should contain the
   * SHA256 checksum of a document
   * */
  def register(data : String) : Future[String] = {
    val registerUrl = "http://www.proofofexistence.com/api/v1/register"
    val dataMap = Map("d" -> data)
    val response = post(registerUrl, dataMap)
    return response
  }

  /**
   * Execute a call to the ProofOfExistence status API
   * to check the status of the given String, which should contain the
   * SHA256 checksum of a document
   * */
  def checkStatus(data : String) : Future[String] = {
    val registerUrl = "http://www.proofofexistence.com/api/v1/status"
    val dataMap = Map("d" -> data)
    val response = post(registerUrl, dataMap)
    return response
  }
}
