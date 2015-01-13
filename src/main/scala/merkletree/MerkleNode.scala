package main.scala.merkletree

import main.scala.hash.Hash

/**
 * Created by fquintanilla on 12-01-15.
 */
abstract class MerkleNode {
  var value : String
  def join(sibling : MerkleNode, hash : Hash) : MerkleNode

}

object MerkleNode {
  /**
   * Generic MerkleNode factory, can instanciate any subclass of MerkleNode
   * For it to work the subclass MUST implements a no-arguments constructor
   * */
  def create[N <: MerkleNode](s : String)(implicit m :Manifest[N]) : N = {
    val node = m.erasure.newInstance().asInstanceOf[N]
    node.value = s
    node
  }

}