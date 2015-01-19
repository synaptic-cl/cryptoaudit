package test.scala

import main.scala.SecureBroadcastChannel._

import org.scalatest.FunSuite

class WalletTest extends FunSuite{

  test("Creating a new address from Bitcoinj"){
    
        
    val newAddress : String = Wallet.getNewAddress();
    
    println(newAddress);
    
    assert(newAddress.length > 10)

  }

}