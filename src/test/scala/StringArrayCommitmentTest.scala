package test.scala

import main.scala.filecommitment.StringArrayCommitment
import main.scala.hash.SHA256Hash
import main.scala.merkletree.MerkleTree
import org.scalatest.FunSuite

/**
 * Created by fquintanilla on 14-01-15.
 */
class StringArrayCommitmentTest extends FunSuite{
  test("Should return different commitments when invoked twice with same input"){
    val testInput = Array("a", "b", "c")
    val firstCommitment = new StringArrayCommitment(testInput)
    val secondCommitment = new StringArrayCommitment(testInput)
    assert(firstCommitment.commitment != secondCommitment.commitment)
  }

  test("proofArray should return an array of strings and valid proofs"){
    val testInput = Array("a", "b", "c")
    val stringArraycommitment = new StringArrayCommitment(testInput)
    val root = stringArraycommitment.root
    val lineProofs = stringArraycommitment.proofArray
    for( (line, proof) <- lineProofs){
      assert(MerkleTree.verify(proof, line, root, SHA256Hash))
    }
  }
}
