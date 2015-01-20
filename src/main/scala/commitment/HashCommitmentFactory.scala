package main.scala.commitment

import main.scala.hash.SHA256Hash
import main.scala.random.RandomGenerator

/**
 * Created by fquintanilla on 14-01-15.
 */
object HashCommitmentFactory extends CommitmentFactory{
  override def commit(value: String): Commitment = {
    val random = RandomGenerator.generate()
    val commitment = SHA256Hash.calculateHash(value + random)
    new Commitment(commitment, random)
  }
}


