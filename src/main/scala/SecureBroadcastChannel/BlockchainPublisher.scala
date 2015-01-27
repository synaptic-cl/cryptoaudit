package main.scala.SecureBroadcastChannel

import java.math.BigInteger

import SecureBroadcastChannel.BitcoinTransaction
import main.scala.http.Post

/**
 * Created by philippe on 27-01-15.
 */
class BlockchainPublisher(val privateKey:BigInteger,val message:String) extends Post {
  
  def publish():String= {

    val transaction = new BitcoinTransaction(privateKey)
    val transactionHexString = transaction.compute(message)
    val transaction_hash = transaction.getTransactionHash()

    val url_pushtx = "http://btc.blockr.io/api/v1/tx/push"
    var data = Map("hex" -> transactionHexString)
    val postPushTx = post(url_pushtx, data)
    
    return transaction_hash

  }

}
