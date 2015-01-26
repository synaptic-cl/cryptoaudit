package test.scala

import java.math.BigInteger
import org.bitcoinj.core.Sha256Hash
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import SecureBroadcastChannel.{BlockchainAPI, BitcoinTransactionException, BitcoinTransaction}


class BitcoinTransactionTest extends FunSuite with MockitoSugar  {


  test("Creating an OP_RETURN transaction from btc key, listunspent, message"){

    val messageInput= "Hi everyone fkdshfkjhsdfhjlskdgfjhksdgfk"
    val spendTxHash = new Sha256Hash("5aeabdff63d243ede0cf64001a9ae5396e12f02eeb78a6e5da2ff54ceb9d7a6b")  //Hash big indian (change order received from blockchain.info ??
    val outputIndex: Int = 0
    val spendableAmount:Int= 32700
    val unspentOutputs = (spendTxHash,outputIndex,spendableAmount)
    var blockchainAPIMock = mock[BlockchainAPI]
    
    when(blockchainAPIMock.getUnspentOutputs("14xfM46j1QR1H1ZEm4XQWjmgYXGcskPC3t")).thenReturn(unspentOutputs)
    
    val bitcoinPrivateKey = new BigInteger("111048528963322230780084975007062377427337875279347986832075827558776062265379")
    val transaction = new BitcoinTransaction(bitcoinPrivateKey)

    transaction.blockchainAPI = blockchainAPIMock
    
    val transactionHex = transaction.compute(messageInput)

    val transactionHexExpected = "01000000016B7A9DEB4CF52FDAE5A678EB2EF0126E39E59A1A0064CFE0ED43D263FFBDEA5A000000006A473044022015DD5BA5514AC3F83CEEA091773B4E7FDF67F9AF5738A5C64321A3DE2F389FF402207A637841EB14CF24F1844A234E8BFEA66DBA780B7DF7884F16B664420A9EDBF101210322497B6527A50F00991F6E29783AF3A4F56277A3311208CF689D0C399F2245C6FFFFFFFF0200000000000000002A6A2848692065766572796F6E6520666B647368666B6A68736466686A6C736B6467666A686B736467666BAC580000000000001976A9142B6F43C1557A062C41D6118888AB918CC41BDEB388AC00000000"
    assert(transactionHexExpected === transactionHex)

  }
  
  test("Raise exception when message is not 40 bytes long"){
    
    val messageInput= "This message is more than 40 bytes long......................................"
    val bitcoinPrivateKey = new BigInteger("111048528963322230780084975007062377427337875279347986832075827558776062265379")
    val transaction = new BitcoinTransaction(bitcoinPrivateKey)
    
    intercept[BitcoinTransactionException] {
      val hexString = transaction.compute(messageInput)
    }
  }

}

