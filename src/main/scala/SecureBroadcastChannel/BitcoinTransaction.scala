package SecureBroadcastChannel

import java.math.BigInteger
import javax.xml.bind.DatatypeConverter

import org.bitcoinj.core._
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.script.{ScriptOpCodes, ScriptBuilder}


class BitcoinTransaction(val aMessage:String, val aPrivateKey:BigInteger) {
  
  var message:String = aMessage
  var privateKey:BigInteger = aPrivateKey
  val networkParams = MainNetParams.get()
  val AMOUNT_MINER_FEE: Int = 10000


  def createKey(): ECKey = {

    val key:ECKey = ECKey.fromPrivate(this.privateKey)

    return key

  }
  
  def getUnspentOutputs():(Sha256Hash,Long,Int) = {
    
    val spendTxHash = new Sha256Hash("5aeabdff63d243ede0cf64001a9ae5396e12f02eeb78a6e5da2ff54ceb9d7a6b")  //Hash big indian (change order received from blockchain.info ??
    val outputIndex: Long = 0
    val spendableAmount:Int= 3270000
    
    return (spendTxHash,outputIndex,spendableAmount)
    
  }
  
  
  def compute():String = {

    val tx = new Transaction(this.networkParams)

    val dataToSend = this.message.getBytes()

    val key = this.createKey()
    val address:Address = key.toAddress(networkParams)

    val stringAddress =address.toString()

    val script = ScriptBuilder.createOutputScript(address)

    val (spendTxHash,outputIndex,spendableAmount) = this.getUnspentOutputs()

    //Create the input
    val prevOut = new TransactionOutPoint(networkParams,outputIndex,spendTxHash)

    //Output where the message is put
    tx.addOutput(Coin.valueOf(0), new ScriptBuilder().op(ScriptOpCodes.OP_RETURN).data(dataToSend).build())


    //Output for change
    val amountMinerFee = Coin.valueOf(this.AMOUNT_MINER_FEE)
    val amountInput = Coin.valueOf(spendableAmount)
    val changeAmount = amountInput.subtract(amountMinerFee)

    tx.addOutput(changeAmount,address)

    tx.addSignedInput(prevOut,script,key)

    val hex = DatatypeConverter.printHexBinary(tx.unsafeBitcoinSerialize());

    return hex
    
  }
  

}
