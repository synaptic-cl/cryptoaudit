package test.scala.secureBroadcastChannel

import SecureBroadcastChannel.BlockchainAPIRest
import org.bitcoinj.core.Sha256Hash
import org.scalatest.FunSuite


class BlockchainAPITest extends FunSuite {

  test("Get unspent outputs") {

    var blockchainAPI = new BlockchainAPIRest()
    val unspentOutput = blockchainAPI.getUnspentOutputs("14hNkmnypQ4yns5nyj8KLh6mysNxr7XGHi")
    
    val unspentOutputExpected = (new Sha256Hash("d00ae84e7c5b54ee84721bc606ee0ac6a13f3a5253ef917de05ab2b9bd3d54bd"),0,3220000)
    
    assert(unspentOutputExpected === unspentOutput)

    
  }
  
}
