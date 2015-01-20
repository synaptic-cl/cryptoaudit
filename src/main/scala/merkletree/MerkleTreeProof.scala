package merkletree

/**
 * Created by fquintanilla on 14-01-15.
 */
case class MerkleTreeProof(val position : Int, val path : List[String]){

  override def equals(other : Any) : Boolean = {
    if (!other.isInstanceOf[MerkleTreeProof]){
      return false
    }
    val that = other.asInstanceOf[MerkleTreeProof]
    that.position == this.position &&
    that.path == this.path
  }
}
