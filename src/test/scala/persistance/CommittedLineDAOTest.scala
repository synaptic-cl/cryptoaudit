package test.scala.persistance

import main.scala.commitment.Commitment
import main.scala.persistance.committedLine.{CommittedLine, CommittedLineDAO}
import main.scala.persistance.transaction.{Transaction, TransactionDAO}
import merkletree.MerkleTreeProof
import org.bson.types.ObjectId
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
* Created by fquintanilla on 19-01-15.
*/
class CommittedLineDAOTest extends FunSuite with BeforeAndAfter{
  val testCommitment : Commitment = new Commitment("test", "test")
  val testProof : MerkleTreeProof = new MerkleTreeProof(0, List("1", "2"))
  var testTransaction: Transaction = _
  var testTransactionId :ObjectId = _
  var testLine : CommittedLine = _
  var testLineId : ObjectId = _

  before{
    CommittedLineDAO.collection.drop()
    TransactionDAO.collection.drop()
    testTransaction = new Transaction("test", testCommitment)
    testTransactionId = TransactionDAO.insert(testTransaction).get
    testLine = new CommittedLine("test", testProof, testTransactionId)
    testLineId = CommittedLineDAO.insert(testLine).get
  }

  test("CommittedLineDAO should be able to insert a valid committedLine and find it in the database"){
    val testProof = new MerkleTreeProof(0, List("1", "2"))
    val testCommittedLine = new CommittedLine("a", testProof, testTransactionId)
    val id = CommittedLineDAO.insert(testCommittedLine)
    assert(id != None)
    val commLineFromDB = CommittedLineDAO.findOneById(id.get)
    assert (commLineFromDB != None)
    assert (testCommittedLine == commLineFromDB.get)
  }

  test("CommittedLineDAO should identify an invalid committedLine and prevent its insertion in the database"){
    val testCommittedLine = new CommittedLine("a", testProof, new ObjectId())
    val id = CommittedLineDAO.insert(testCommittedLine)
    assert (id == None)
  }

  test("Method findByLine should return all committedLines with the specified line value"){
    val result = CommittedLineDAO.findByLine("test")
    assert(result.count == 1)
    val line = result.toList.head
    assert (line == testLine)
  }
  test("Method findByTransactionId should return all committedLines with the specified commitmentId"){
    val secondLine = new CommittedLine("test2", testProof, testTransactionId)
    assert(CommittedLineDAO.insert(secondLine).get != None)
    val posibleLines = List(testLine, secondLine)
    val result = CommittedLineDAO.findByTransactionId(testTransactionId)
    assert(result.count == 2)
    for (commLine <- result){
      assert(posibleLines.contains(commLine))
    }
  }
  test("Method findByTransaction should return all committedLines with the id of the specified commitment"){
    val secondLine = new CommittedLine("test2", testProof, testTransactionId)
    assert(CommittedLineDAO.insert(secondLine).get != None)
    val posibleLines = List(testLine, secondLine)
    val result = CommittedLineDAO.findByTransaction(testTransaction)
    assert(result.count == 2)
    for (commLine <- result){
      assert(posibleLines.contains(commLine))
    }
  }
  test("Method findTransactionFromLine should return the transaction the specified line references"){
    val result = CommittedLineDAO.findTransactionFromLine(testLine)
    assert(result != None)
    assert(result.get == testTransaction)
  }
  test("Method findTransactionFromLineId should return the commitment the specified line id references"){
    val result = CommittedLineDAO.findTransactionFromLineId(testLineId)
    assert(result != None)
    assert(result.get == testTransaction)
  }
  test("Method findTransactionFromLine should return None when it can't find the commitment"){
    val secondLine = new CommittedLine("test2", testProof, new ObjectId())
    val result = CommittedLineDAO.findTransactionFromLine(secondLine)
    assert(result == None)
  }
  test("Method findTransactionFromLineId should return None when it can't find the commitment"){
    val secondLineId = new ObjectId()
    val result = CommittedLineDAO.findTransactionFromLineId(secondLineId)
    assert(result == None)
  }


}
