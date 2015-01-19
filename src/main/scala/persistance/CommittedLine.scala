package main.scala.persistance

import merkletree.MerkleTreeProof
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 19-01-15.
 */
case class CommittedLine(val line : String, proof : MerkleTreeProof, commitment : ObjectId)
