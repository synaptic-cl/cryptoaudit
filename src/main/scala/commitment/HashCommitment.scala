package commitment

import main.scala.hash.SHA256Hash
import random.RandomNumberGenerator

/**
 * Created by fquintanilla on 14-01-15.
 */
object HashCommitment extends Commitment{
  override def commit(value: String): (String, String) = {
    val random = RandomNumberGenerator.generate()
    val commitment = SHA256Hash.calculateHash(value + random)
    (commitment, random)
  }
}
