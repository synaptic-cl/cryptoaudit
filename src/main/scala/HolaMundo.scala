import org.bouncycastle.crypto.digests.SHA1Digest
import org.bouncycastle.util.encoders.Base64

/**
 * @author fquintanilla
 */
object HolaMundo {
  def main(args: Array[String]) {
    var message = "Hola mundo"
    println(message)

    var rawOctets: Array[Byte] = message.getBytes()
    var digest: SHA1Digest = new SHA1Digest();
    var digestOctets: Array[Byte] = new Array[Byte](digest.getDigestSize())
    digest.update(rawOctets, 0, rawOctets.length);
    digest.doFinal(digestOctets, 0);
    println(Base64.encode(digestOctets))
  }
}