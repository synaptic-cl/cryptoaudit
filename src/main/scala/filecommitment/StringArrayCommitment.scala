package main.scala.filecommitment

import main.scala.commitment.HashCommitmentFactory
import main.scala.hash.SHA256Hash
import main.scala.merkletree.{SimpleMerkleNode, MerkleTree}
import merkletree.MerkleTreeProof

/**
 * Created by fquintanilla on 14-01-15.
 */

class StringArrayCommitment(val lines : Array[String]) {
  val merkleTree = new MerkleTree[SimpleMerkleNode](lines, SHA256Hash)
  val root = merkleTree.root

  val commitment = HashCommitmentFactory.commit(root)

  def proofArray : Array[(String, MerkleTreeProof)] = {
    for (line <- lines) yield (line, merkleTree.computeProof(line).get)
  }

}
