package main.scala.hash

import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.util.encoders.Hex

/**
 * Created by fquintanilla on 09-01-15.
 */
object SHA256Hash extends Hash{
  override def calculateHash (input : String) : String = {
    val digest : SHA256Digest = new SHA256Digest()
    val inputAsBytes = input.getBytes
    digest.update(inputAsBytes, 0, inputAsBytes.length)
    val outputAsBytes = new Array[Byte](digest.getDigestSize)
    digest.doFinal(outputAsBytes, 0)
    val outputAsString = new String(Hex.encode(outputAsBytes), "UTF-8")
    outputAsString
  }
}
