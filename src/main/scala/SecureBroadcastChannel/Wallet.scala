package main.scala.SecureBroadcastChannel

import org.bitcoinj.core._
import org.bitcoinj.params.TestNet2Params

object Wallet {
  
  def getNewAddress(): String = {
    
    val netParams = TestNet2Params.get()
    val key: ECKey = new ECKey
    val addressFromKey: Address = key.toAddress(netParams)
    val stringAddress = addressFromKey.toString
    
    return stringAddress
    
  }

}
