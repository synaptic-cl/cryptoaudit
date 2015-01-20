package main.scala.commitment

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 19-01-15.
 */
case class Commitment (value : String, val random : String, @Key("_id") _id : ObjectId = new ObjectId)
