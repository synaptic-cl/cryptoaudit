package main.scala.persistance.committedLine

import merkletree.MerkleTreeProof
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 19-01-15.
 *
 * Case class to represent a line of a file which has been committed
 * used to associate each line to its corresponding MerkleTreeProof in the database
 */
case class CommittedLine(val line : String, proof : MerkleTreeProof, transaction : ObjectId)
