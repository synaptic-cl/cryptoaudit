package main.scala.merkletree

import main.scala.hash.Hash

/**
 * Created by fquintanilla on 12-01-15.
 */
abstract class MerkleNode(val value : String) {
  def join(sibling : MerkleNode, hash : Hash) : MerkleNode

}
