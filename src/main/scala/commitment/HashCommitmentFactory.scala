package main.scala.commitment

import main.scala.hash.SHA256Hash
import main.scala.random.RandomGenerator

/**
 * Created by fquintanilla on 14-01-15.
 *
 * Creates commitments using a hash function
 */
object HashCommitmentFactory extends CommitmentFactory{
  /**
   * Uses a hash function to create a commitment
   * Comm(a) = H(a||r) where r is a random value
   * */
  override def commit(value: String): Commitment = {
    val random = RandomGenerator.generate()
    val commitment = SHA256Hash.calculateHash(value + random)
    new Commitment(commitment, random)
  }
}


