import main.scala.commitment.Commitment
import main.scala.persistance.CommitmentDAO
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by fquintanilla on 19-01-15.
 */
class CommitmentDAOTest extends FunSuite with BeforeAndAfter {

  before {
    CommitmentDAO.collection.drop()
  }
  test("CommitmentDAO should be able to insert a valid commitment and find it in the database") {
    val commitment = new Commitment("a", "b")
    val commitmentId = CommitmentDAO.insert(commitment)
    assert(commitmentId != None)
    val commitmentFromDB = CommitmentDAO.findOneById(commitmentId.get)
    assert(commitmentFromDB != None)
    assert(commitment == commitmentFromDB.get)
  }

  test("The findByCommitmentValue method should return the commitment with the specified value if it exists in the database") {
    val testValue = "test"
    val testCommitment = new Commitment(testValue, "random")
    val id = CommitmentDAO.insert(testCommitment)
    assert(id != None)
    val commitmentFromBD = CommitmentDAO.findByCommitmentValue(testValue)
    assert(commitmentFromBD != None)
    assert(testCommitment == commitmentFromBD.get)
  }

  test("The findByCommitmentValue method should return none when the specified value doesn't exist in the database") {
    val testValue = "test"
    val commitmentFromDB = CommitmentDAO.findByCommitmentValue(testValue)
    assert(commitmentFromDB == None)
  }

}