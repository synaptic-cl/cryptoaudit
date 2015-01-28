package test.scala

import main.scala.commitment.Commitment
import main.scala.persistance.transaction.{TransactionDAO, Transaction}
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by fquintanilla on 19-01-15.
 */
class TransactionDAOTest extends FunSuite with BeforeAndAfter {
  var testTransaction:Transaction = _
  val testValue = "test"
  val testRandom = "random"
  val testCommitment = new Commitment(testValue, testRandom)

  before {
    TransactionDAO.collection.drop()
    testTransaction = new Transaction("000", testCommitment)
  }
  test("CommitmentDAO should be able to insert a valid commitment and find it in the database") {
    val transactionId = TransactionDAO.insert(testTransaction)
    assert(transactionId != None)
    val transactionFromDB = TransactionDAO.findOneById(transactionId.get)
    assert(transactionFromDB != None)
    assert(testTransaction == transactionFromDB.get)
  }

  test("The findByCommitmentValue method should return the commitment with the specified value if it exists in the database") {
    val id = TransactionDAO.insert(testTransaction)
    assert(id != None)
    val transactionFromBD = TransactionDAO.findByCommitmentValue(testValue)
    assert(transactionFromBD != None)
    assert(testTransaction == transactionFromBD.get)
  }

  test("The findByCommitmentValue method should return none when the specified value doesn't exist in the database") {
    val commitmentFromDB = TransactionDAO.findByCommitmentValue(testValue)
    assert(commitmentFromDB == None)
  }
  test("The getIdFromValue method should return a valid id corresponding to the transaction with the given commitment's value"){
    val id = TransactionDAO.insert(testTransaction)
    assert(id != None)
    val actualId = TransactionDAO.getIdFromValue(testValue)
    assert (actualId != None)
    assert (actualId.get == id.get)
  }
  test("The getIdFromValue method should return None if the given value doesn't correspond to any commitment in the database"){
    val actualId = TransactionDAO.getIdFromValue(testValue)
    assert (actualId == None)
  }

}