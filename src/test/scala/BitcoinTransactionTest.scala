package test.scala

import java.math.BigInteger
import org.bitcoinj.core.Sha256Hash
import org.scalatest.FunSuite
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfter

import SecureBroadcastChannel.{BlockchainAPI, BitcoinTransactionException, BitcoinTransaction}


class BitcoinTransactionTest extends FunSuite with BeforeAndAfter with MockFactory  {

  var transaction:BitcoinTransaction = _
  
  before {
    val bitcoinPrivateKey = new BigInteger("111048528963322230780084975007062377427337875279347986832075827558776062265379")
    transaction = new BitcoinTransaction(bitcoinPrivateKey)
    
    //Defining mock Blockchain API
    val blockchainAPIMock = mock[BlockchainAPI]

    val spendTxHash = new Sha256Hash("5aeabdff63d243ede0cf64001a9ae5396e12f02eeb78a6e5da2ff54ceb9d7a6b")  //Hash big indian (change order received from blockchain.info ??
    val outputIndex: Int = 0
    val spendableAmount:Int= 3270000
    val unspentOutputs = (spendTxHash,outputIndex,spendableAmount)
    blockchainAPIMock.getUnspentOutputs(val address:String) = mockFunction[String => (Sha256Hash,Int,Int)]

    
    transaction.blockchainAPI = blockchainAPIMock


  }
  
  
  test("Creating an OP_RETURN transaction from btc key, listunspent, message"){

    
    
    
    val messageInput= "Hi everyone fkdshfkjhsdfhjlskdgfjhksdgfk"

    val transactionHex = transaction.compute(messageInput)

    val transactionHexExpected = "01000000016B7A9DEB4CF52FDAE5A678EB2EF0126E39E59A1A0064CFE0ED43D263FFBDEA5A000000006A473044022017410C7E9893DFB311572A360B2B2F2297BA885DFC526F2E256380676DD5861402207F0B9FD9BBBD4B75339EBD827F892EB4B35CAAC474DC22B1C22B4CCAE07DFBF801210322497B6527A50F00991F6E29783AF3A4F56277A3311208CF689D0C399F2245C6FFFFFFFF0200000000000000002A6A2848692065766572796F6E6520666B647368666B6A68736466686A6C736B6467666A686B736467666B60BE3100000000001976A9142B6F43C1557A062C41D6118888AB918CC41BDEB388AC00000000"
    assert(transactionHexExpected === transactionHex)

  }
  
  test("Raise exception when message is not 40 bytes long"){
    
    val messageInput= "This message is more than 40 bytes long......................................"
    val bitcoinPrivateKey = new BigInteger("111048528963322230780084975007062377427337875279347986832075827558776062265379")

    intercept[BitcoinTransactionException] {
      val hexString = transaction.compute(messageInput)
    }
  }

}

