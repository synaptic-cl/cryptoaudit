package test.scala.persistance

import main.scala.commitment.Commitment
import main.scala.persistance.file.{File, FileDAO}
import main.scala.persistance.transaction.{TransactionDAO, Transaction}
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by fquintanilla on 28-01-15.
 */
class FileDAOTest extends FunSuite with BeforeAndAfter {
  var testFile : File = _
  val testFilename = "filename"
  val testCommitment = new Commitment("test", "random")
  val testTransaction = new Transaction("000", testCommitment)

  before {
    FileDAO.collection.drop()
    TransactionDAO.collection.drop()
    TransactionDAO.insert(testTransaction)
    testFile = new File(None, testFilename)
  }

  test("FileDAO should be able to insert a File without a Transaction and find it later"){
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val fileFromBD = FileDAO.findOneById(id.get)
    assert(fileFromBD != None)
    assert(fileFromBD.get == testFile)
  }

  test("FileDAO should be able to insert a File with a specific Transaction and find it later"){
    var testFile = new File(Some(testTransaction._id), testFilename)
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val fileFromBD = FileDAO.findOneById(id.get)
    assert(fileFromBD != None)
    assert(fileFromBD.get == testFile)
  }

  test("The setTransaction method should be able to update the transaction value on a file with a valid Transaction"){
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val result = FileDAO.setTransaction(testFile, testTransaction)
    assert(result)
    val fileFromBD = FileDAO.findOneById(id.get)
    assert(fileFromBD != None)
    assert(fileFromBD.get.tx_id != None)
    assert(fileFromBD.get.tx_id.get == testTransaction._id)
  }

  test("The setTransaction method shouldn't update a file with an invalid Transaction"){
    // Create a new Transaction without inserting it to the database
    val invalidTransaction = new Transaction("001", testCommitment)
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val result = FileDAO.setTransaction(testFile, invalidTransaction)
    assert(!result)
    val fileFromBD = FileDAO.findOneById(id.get)
    assert(fileFromBD != None)
    assert(fileFromBD.get.tx_id == None)
  }

  test("The getTransaction method should return the corresponding Transaction object if tx_id is set"){
    var testFile = new File(Some(testTransaction._id), testFilename)
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val tx = FileDAO.getTransaction(testFile)
    assert (tx != None)
    assert (tx.get._id == testFile.tx_id.get)
  }
  test("The getTransaction method should return None if tx_id is not set"){
    val id = FileDAO.insert(testFile)
    assert(id != None)
    val tx = FileDAO.getTransaction(testFile)
    assert (tx == None)
  }

}
