package main.scala.merkletree

import main.scala.hash.Hash

/**
 * Created by fquintanilla on 12-01-15.
 */
class MerkleTree[N <: MerkleNode](elements : IndexedSeq[String], hash : Hash){

  /**
   * Calculates the height required to contain the given IndexedSeq
   * */
  private def requiredHeight(strings: IndexedSeq[String]): Int = {
    val size = strings.length
    def log2(x: Double): Double = math.log(x) / math.log(2)
    math.ceil(log2(size)).toInt + 1
  }

  val height : Int = requiredHeight(elements)
  val levels : Array[Array[MerkleNode]] = new Array[Array[MerkleNode]](height)
  //Initialize lowest level with the hashes ef the given elements
  levels(height-1) = new Array[MerkleNode](elements.length)
  for( i <- 0 until elements.length){
    levels(height-1)(i) = new SimpleMerkleNode(hash.calculateHash(elements(i)))
  }
  for(currentLevel <- (0 until height-1).reverse){

    //The current level is half the size of the previous one (rounded up)
    val currentLevelSize = (levels(currentLevel+1).length+1)/2
    levels(currentLevel) = new Array[MerkleNode](currentLevelSize)
    for(j <- 0 until currentLevelSize){
        if(2*j+1 < levels(currentLevel+1).length)
          levels(currentLevel)(j) = levels(currentLevel+1)(2*j).join(levels(currentLevel+1)(2*j+1),hash)
        else
          levels(currentLevel)(j) = levels(currentLevel+1)(2*j)
    }
  }

  def root : String = {
    levels(0)(0).value
  }




}
