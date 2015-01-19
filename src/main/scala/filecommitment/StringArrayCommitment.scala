package filecommitment

import commitment.HashCommitment
import main.scala.hash.SHA256Hash
import main.scala.merkletree.{SimpleMerkleNode, MerkleTree}
import merkletree.MerkleTreeProof

/**
 * Created by fquintanilla on 14-01-15.
 */

class StringArrayCommitment(val lines : Array[String]) {
  val merkleTree = new MerkleTree[SimpleMerkleNode](lines, SHA256Hash)
  val root = merkleTree.root

  val (commitment,random) = HashCommitment.commit(root)

  def proofArray : Array[(String, MerkleTreeProof)] = {
    for (line <- lines) yield (line, merkleTree.computeProof(line).get)
  }

}
