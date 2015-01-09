package main.scala.hash

import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.util.encoders.Hex

/**
 * Created by fquintanilla on 09-01-15.
 */
object SHA256Hash extends Hash{
  override def hash (input : String) : String = {
    var digest : SHA256Digest = new SHA256Digest()
    var inputAsBytes = input.getBytes()
    digest.update(inputAsBytes, 0, inputAsBytes.length)
    var outputAsBytes = new Array[Byte](digest.getDigestSize())
    digest.doFinal(outputAsBytes, 0)
    var outputAsString = new String(Hex.encode(outputAsBytes), "UTF-8")
    return outputAsString
  }
}
