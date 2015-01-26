package SecureBroadcastChannel

import java.math.BigInteger
import javax.xml.bind.DatatypeConverter

import org.bitcoinj.core._
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.script.{ScriptOpCodes, ScriptBuilder}

class BitcoinTransactionException(smth:String)  extends Exception

class BitcoinTransaction(val aPrivateKey:BigInteger) {

  var privateKey:BigInteger = aPrivateKey
  val networkParams = MainNetParams.get()
  val AMOUNT_MINER_FEE: Int = 10000
  val EXPECTED_LENGHT_FOR_OP_RETURN_MESSAGES: Int = 40
  var blockchainAPI:BlockchainAPI = new BlockchainAPIRest()

  def createKey(): ECKey = {

    val key:ECKey = ECKey.fromPrivate(this.privateKey)

    return key

  }

  
  def compute(aMessage:String):String = {

    val tx = new Transaction(this.networkParams)

    val dataToSend = aMessage.getBytes()
    if (dataToSend.length != EXPECTED_LENGHT_FOR_OP_RETURN_MESSAGES)
      throw new BitcoinTransactionException("The length of message to be published with OP_RETURN must be equal to " + EXPECTED_LENGHT_FOR_OP_RETURN_MESSAGES.toString())

    val key = this.createKey()
    val address:Address = key.toAddress(networkParams)

    val stringAddress =address.toString()

    val script = ScriptBuilder.createOutputScript(address)

    val (spendTxHash,outputIndex,spendableAmount) = this.blockchainAPI.getUnspentOutputs(stringAddress)

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
