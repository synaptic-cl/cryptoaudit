package test.scala

import SecureBroadcastChannel.BlockchainAPIRest
import org.bitcoinj.core.Sha256Hash
import org.scalatest.FunSuite


class BlockchainAPITest extends FunSuite {

  test("Get unspent outputs") {

    var blockchainAPI = new BlockchainAPIRest()
    val unspentOutput = blockchainAPI.getUnspentOutputs("14hNkmnypQ4yns5nyj8KLh6mysNxr7XGHi")
    
    val unspentOutputExpected = (new Sha256Hash("9709cc231ac8e991bcbb8e5105492740de1ac38aaf1badfdb639b26e82453015"),0,4230000)
    
    assert(unspentOutputExpected === unspentOutput)

    
  }
  
}
