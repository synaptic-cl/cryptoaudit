package SecureBroadcastChannel

import org.bitcoinj.core.Sha256Hash

/**
 * Created by philippe on 23-01-15.
 */
class BlockchainAPI {

  def getUnspentOutputs(address:String):(Sha256Hash,Int,Int) = {

    val tuple = (new Sha256Hash("5aeabdff63d243ede0cf64001a9ae5396e12f02eeb78a6e5da2ff54ceb9d7a6b"),0  ,32700)
    
    return tuple
  }
  

}
