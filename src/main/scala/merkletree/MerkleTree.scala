package main.scala.merkletree

import main.scala.hash.Hash
import merkletree.MerkleTreeProof

/**
 * Created by fquintanilla on 12-01-15.
 */
class MerkleTree[N <: MerkleNode](elements : IndexedSeq[String], hash : Hash)(implicit m : Manifest[N]){

  /**
   * String used to fill the trees if the length of the sequence given is
   * not a power of two*/
  private val filler = "-"


  val height : Int = requiredHeight(elements)
  val levels : Array[Array[MerkleNode]] = new Array[Array[MerkleNode]](height)
  //Initialize lowest level with the hashes ef the given elements
  //If elements.length is not a power of 2 fill the remaining space with "-"
  levels(height-1) = new Array[MerkleNode](Math.pow(2,height-1).toInt)
  val lowestLevel = levels(height-1)
  for( i <- 0 until elements.length){
    lowestLevel(i) = MerkleNode.create[N](hash.calculateHash(elements(i)))
  }
  for (i <- elements.length until lowestLevel.length)
    lowestLevel(i) = MerkleNode.create[N](hash.calculateHash(filler))

  for(currentLevel <- (0 until height-1).reverse){

    //The current level is half the size of the previous one (rounded up)
    val currentLevelSize = (levels(currentLevel+1).length)/2
    levels(currentLevel) = new Array[MerkleNode](currentLevelSize)
    for(j <- 0 until currentLevelSize){
        if(2*j+1 < levels(currentLevel+1).length)
          levels(currentLevel)(j) = levels(currentLevel+1)(2*j).join(levels(currentLevel+1)(2*j+1),hash)
        else
          levels(currentLevel)(j) = levels(currentLevel+1)(2*j)
    }
  }

  /**
   * Calculates the height required to contain the given IndexedSeq
   * */
  private def requiredHeight(strings: IndexedSeq[String]): Int = {
    val size = strings.length
    def log2(x: Double): Double = math.log(x) / math.log(2)
    math.ceil(log2(size)).toInt + 1
  }

  /**
   * Returns a string representing the root of the tree
   * */
  def root : String = {
    levels(0)(0).value
  }

  def getLevels : Array[Array[String]] = for (row <- levels) yield for (column <- row) yield column.value

  /**
   * Returns an Option with an array of Strings representing the proof if the given String
   * belongs to the sequence used to form the tree or None if if it doesn't belong to the sequence
   * */
  def computeProof(element: String) : Option[MerkleTreeProof] = {
    val elmtHash = hash.calculateHash(element)
    var index : Int = levels(height-1).indexWhere(_.value == elmtHash)
    val elmntIndex = index
    if (index == -1) return None

    val proof : Array[String] = new Array[String](height-1)
    var currentLevel = height-1
    while(currentLevel > 0){
      var sibling = if (index%2 == 0) index+1 else index-1
      index /= 2
      proof(currentLevel-1) = levels(currentLevel)(sibling).value
      currentLevel -= 1
    }
    val path = proof.reverse
    Some(new MerkleTreeProof(elmntIndex, path))
  }

}

object MerkleTree{
  def verify(proof : MerkleTreeProof, elmt : String, root : String, hash : Hash) : Boolean = {
    val left : String = "0"
    val right : String = "1"
    var position = proof.position.toBinaryString
    val pathLength = proof.path.length
    position = "0" * (pathLength-position.length) + position
    position = position.reverse
    var currentHash = hash.calculateHash(elmt)
    for ((leftOrRight , sibling) <- position zip proof.path){
      currentHash = if (leftOrRight.toString == left) currentHash + sibling else sibling + currentHash
      currentHash = hash.calculateHash(currentHash)
    }
    currentHash == root
  }
}
