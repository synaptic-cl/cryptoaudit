package SecureBroadcastChannel


import main.scala.http.Get
import net.liftweb.json
import net.liftweb.json.JsonParser
import org.bitcoinj.core.Sha256Hash



trait BlockchainAPI {

  def getUnspentOutputs(address:String):(Sha256Hash,Int,Int)

}


/**
 * Class with the methods used to connect with the BlockChain REST API
 *  described in https://blockchain.info/api
 * */
class BlockchainAPIRest extends BlockchainAPI with Get{

  def getUnspentOutputs(address:String):(Sha256Hash,Int,Int) = {

    val registerUrl = "https://blockchain.info/unspent"
    val dataMap = Map("address" -> address)
    val response = get(registerUrl, dataMap)
    
    //Parsing JSON
    var jsonResponse= JsonParser.parse(response)
    var unspentOutputs = jsonResponse.values.asInstanceOf[Map[String,List[Map[String,Any]]]]("unspent_outputs")(0)
    
    val transaction_hash: String = unspentOutputs("tx_hash_big_endian").toString
    val amount_in_satoshis: Int = unspentOutputs("value").toString.toInt
    val transaction_index: Int = unspentOutputs("tx_output_n").toString.toInt
    
    val tuple = (new Sha256Hash(transaction_hash),transaction_index ,amount_in_satoshis)
    
    return tuple
  }
}
