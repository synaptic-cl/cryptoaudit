package main.scala.merkletree

import main.scala.hash.Hash

/**
 * Created by fquintanilla on 12-01-15.
 */
case class SimpleMerkleNode(override val value: String) extends MerkleNode(value) {
  override def join(sibling: MerkleNode, hash: Hash): MerkleNode = {
    val parentValue = hash.calculateHash(this.value + sibling.value)
    new SimpleMerkleNode(parentValue)
  }
}

