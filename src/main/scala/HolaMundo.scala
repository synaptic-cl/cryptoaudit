import org.bouncycastle.crypto.digests.SHA1Digest
import org.bouncycastle.util.encoders._
import dispatch._, Defaults._

package main.scala{

import org.bouncycastle.crypto.digests.SHA256Digest


/**
 * @author fquintanilla
 */
object HolaMundo {

  def mySha1(message : String): Array[Byte] ={
    var rawOctets: Array[Byte] = message.getBytes()
    var digest: SHA1Digest = new SHA1Digest()
    var digestOctets: Array[Byte] = new Array[Byte](digest.getDigestSize())
    digest.update(rawOctets, 0, rawOctets.length)
    digest.doFinal(digestOctets, 0)
    var hash : Array[Byte] = Base64.encode(digestOctets)
    return hash
  }

  def SHA256(input : String) : String = {
    var digest : SHA256Digest = new SHA256Digest()
    var inputAsBytes = input.getBytes()
    digest.update(inputAsBytes, 0, inputAsBytes.length)
    var outputAsBytes = new Array[Byte](digest.getDigestSize())
    digest.doFinal(outputAsBytes, 0)
    var outputAsString = new String(Hex.encode(outputAsBytes), "UTF-8")
    return outputAsString
  }

  def main(args: Array[String]) {
    var message = "asdf"
    println(message)
    val hash = SHA256(message)
    println(hash)
    for {
      registerResponse <- register(hash)
      checkStatusResponse <- checkStatus(hash)
    }{
      println(registerResponse)
      println(checkStatusResponse)
    }


  }

  def post(hostUrl : String, data: Map[String, String]): Future[String] = {
    val request = url(hostUrl)
    def postRequest = request << data
    val response : Future[String] = Http(postRequest OK as.String)
    return response
  }

  def register(data : String) : Future[String] = {
    val registerUrl = "http://www.proofofexistence.com/api/v1/register"
    val dataMap = Map("d" -> SHA256(data))
    val response = post(registerUrl, dataMap)
    return response
  }

  def checkStatus(data : String) : Future[String] = {
    val registerUrl = "http://www.proofofexistence.com/api/v1/status"
    val dataMap = Map("d" -> SHA256(data))
    val response = post(registerUrl, dataMap)
    return response
  }
}
}
