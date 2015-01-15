package main.scala.merkletree

import main.scala.hash.Hash

/**
 * Created by fquintanilla on 12-01-15.
 */

/**
 * Simple implementation of the MerkleNode trait, the value of a parent is simply
 * the resulting hash of the concatenation of its children
 * */
class SimpleMerkleNode(var value: String) extends MerkleNode(){
  override def join(sibling: MerkleNode, hash: Hash): MerkleNode = {
    val parentValue = hash.calculateHash(this.value + sibling.value)
    new SimpleMerkleNode(parentValue)
  }

  /**
   * Empty constructor required to instanciate with reflection
   * */
  def this() = {
    this(value="")
  }
}

