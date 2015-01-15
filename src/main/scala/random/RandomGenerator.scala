package random

import org.bouncycastle.util.encoders.Hex

/**
 * Created by fquintanilla on 14-01-15.
 */
object RandomGenerator {

  /**
   * Generates a secure random number with the specified amount of bits (rounded to bytes)
   * and returns a String with the Hex representation of the number. The length of the generated string
   * is 1/4 of the specified number of bits (rounded)
   * */
  def generate(size : Int = 128) : String = {
    import java.security.SecureRandom
    val ranGen = new SecureRandom
    val randomBytes : Array[Byte] = new Array[Byte](size/8) // 8 bytes = 64 bits
    ranGen.nextBytes(randomBytes)
    new String(Hex.encode(randomBytes), "UTF-8")
  }
}
