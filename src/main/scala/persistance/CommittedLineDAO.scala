package main.scala.persistance

import com.novus.salat.dao.SalatDAO
import org.bson.types.ObjectId
import com.novus.salat.global._

/**
 * Created by fquintanilla on 19-01-15.
 */
object CommittedLineDAO extends SalatDAO[CommittedLine, ObjectId](collection = MongoFactory.mongoDB("CommittedLine")){

  override def insert(toPersist : CommittedLine) : Option[ObjectId] = {
    if (validate(toPersist)){
      super.insert(toPersist)
    }
    else{
      None
    }
  }
  /**
   * Method used to validate a committedLine to be persisted
   * returns true if toPersist is a valid CommittedLine and false otherwise
   * */
  private def validate(toPersist : CommittedLine) : Boolean = {
    /* A valid CommittedLine should have no empty fields and
    * reference a valid commitment from the database */
    val commitmentId = toPersist.commitment
    val commitment = CommitmentDAO.findOneById(commitmentId)
    if (commitment.getOrElse("") == "") return false
    true
   }


}
