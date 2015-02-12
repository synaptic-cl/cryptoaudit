package main.scala.filecommitment

import main.scala.commitment.HashCommitmentFactory
import main.scala.hash.SHA256Hash
import main.scala.merkletree.{SimpleMerkleNode, MerkleTree}
import merkletree.MerkleTreeProof

/**
 * Created by fquintanilla on 14-01-15.
 *
 * Creates a commitment from a String Array (which can be obtained from a file)
 * using a merkle tree where each string from the array is a leaf of the tree
 */

class StringArrayCommitment(val lines : Array[String]) {
  private val EXPECTED_LENGHT_FOR_OP_RETURN_MESSAGES: Int = 40


  private val merkleTree = new MerkleTree[SimpleMerkleNode](lines, SHA256Hash)
  val root = merkleTree.root

  private val commitmentObject = HashCommitmentFactory.commit(root)
    val commitment = commitmentObject.value.substring(0,EXPECTED_LENGHT_FOR_OP_RETURN_MESSAGES)
  val random = commitmentObject.random

  /**
   * Computes and returns an Array of pairs where each element is a
   * string of the original array and its corresponding MerkleTreeProof
   * */
  def proofArray : Array[(String, MerkleTreeProof)] = {
    for (line <- lines) yield (line, merkleTree.computeProof(line).get)
  }

}
