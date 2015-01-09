package main.scala

import main.scala.http.ProofOfExistence
import main.scala.hash.SHA256Hash._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author fquintanilla
 */
object HolaMundo {

  def main(args: Array[String]) {
    val message = "asdf"
    println(message)
    val hashResult = calculateHash(message)
    println(hashResult)
    for {
      registerResponse <- ProofOfExistence.register(hashResult)
      checkStatusResponse <- ProofOfExistence.checkStatus(hashResult)
    }{
      println(registerResponse)
      println(checkStatusResponse)
    }
  }




}
