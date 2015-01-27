package main.scala

import java.math.BigInteger

import main.scala.SecureBroadcastChannel.BlockchainPublisher
import main.scala.http.ProofOfExistence
import main.scala.hash.SHA256Hash._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author fquintanilla
 */
object HolaMundo {

  def main(args: Array[String]) {
    
    var blockchainPublisher = new BlockchainPublisher(new BigInteger("111048528963322230780084975007062377427337875279347986832075827558776062265379"), 
                                                      "Hola Mundo desde Cryptoaudit............")
    val transactionHash = blockchainPublisher.publish()
    
    println(transactionHash)

  }

}
