package main.scala.persistance

import com.novus.salat.dao.SalatDAO
import main.scala.commitment.Commitment
import main.scala.persistance.MongoFactory
import org.bson.types.ObjectId
import com.novus.salat.global._

/**
 * Created by fquintanilla on 19-01-15.
 */
object CommitmentDAO extends SalatDAO[Commitment, ObjectId](collection = MongoFactory.mongoDB("Commitment"))
