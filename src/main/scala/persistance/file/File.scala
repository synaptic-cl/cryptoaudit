package main.scala.persistance.file

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId

/**
 * Created by fquintanilla on 28-01-15.
 *
 * Case class to represent a File which hash been committed and published into the SBC
 */
case class File (var tx_id : Option[ObjectId], val filename : String, @Key("_id") _id : ObjectId = new ObjectId)