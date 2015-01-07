import org.bouncycastle.crypto.digests.SHA1Digest
import org.bouncycastle.util.encoders.Base64

/**
 * @author fquintanilla
 */
object HolaMundo {

  def mySha1(message : String): Array[Byte] ={
    var rawOctets: Array[Byte] = message.getBytes()
    var digest: SHA1Digest = new SHA1Digest();
    var digestOctets: Array[Byte] = new Array[Byte](digest.getDigestSize())
    digest.update(rawOctets, 0, rawOctets.length);
    digest.doFinal(digestOctets, 0);
    var hash : Array[Byte] = Base64.encode(digestOctets)
    return hash
  }

  def main(args: Array[String]) {
    var message = "Hola mundo"
    println(message)
    println(mySha1(message))
  }
}