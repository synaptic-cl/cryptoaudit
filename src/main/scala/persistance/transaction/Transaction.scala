package main.scala.persistance.transaction

import com.novus.salat.annotations.raw.Key
import main.scala.commitment.Commitment
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 27-01-15.
 */

case class Transaction(val transaction : String, val commitment : Commitment, @Key("_id") _id : ObjectId = new ObjectId)
