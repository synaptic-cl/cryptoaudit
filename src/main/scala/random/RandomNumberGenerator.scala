package random

import org.bouncycastle.util.encoders.Hex

/**
 * Created by fquintanilla on 14-01-15.
 */
object RandomNumberGenerator {
  def generate(size : Int = 128) : String = {
    import java.security.SecureRandom
    val ranGen = new SecureRandom
    val randomBytes : Array[Byte] = new Array[Byte](size/8) // 8 bytes = 64 bits
    ranGen.nextBytes(randomBytes)
    new String(Hex.encode(randomBytes), "UTF-8")
  }
}
