import main.scala.commitment.Commitment
import main.scala.persistance.{CommittedLine, CommitmentDAO, CommittedLineDAO}
import merkletree.MerkleTreeProof
import org.bson.types.ObjectId
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by fquintanilla on 19-01-15.
 */
class CommittedLineDAOTest extends FunSuite with BeforeAndAfter{
  var testCommitment : Commitment = _
  var testCommitmentId :ObjectId = _
  var testProof : MerkleTreeProof = _
  var testLineId : ObjectId = _
  var testLine : CommittedLine = _

  before{
    CommittedLineDAO.collection.drop()
    CommitmentDAO.collection.drop()
    testCommitment = new Commitment("test", "test")
    testCommitmentId = CommitmentDAO.insert(testCommitment).get
    testProof = new MerkleTreeProof(0, List("1", "2"))
    testLine = new CommittedLine("test", testProof, testCommitmentId)
    testLineId = CommittedLineDAO.insert(testLine).get
  }

  test("CommittedLineDAO should be able to insert a valid committedLine and find it in the database"){
    val testProof = new MerkleTreeProof(0, List("1", "2"))
    val testCommittedLine = new CommittedLine("a", testProof, testCommitmentId)
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
  test("Method findByCommitmentId should return all committedLines with the specified commitmentId"){
    val secondLine = new CommittedLine("test2", testProof, testCommitmentId)
    assert(CommittedLineDAO.insert(secondLine).get != None)
    val posibleLines = List(testLine, secondLine)
    val result = CommittedLineDAO.findByCommitmentId(testCommitmentId)
    assert(result.count == 2)
    for (commLine <- result){
      assert(posibleLines.contains(commLine))
    }
  }
  test("Method findByCommitment should return all committedLines with the id of the specified commitment"){
    val secondLine = new CommittedLine("test2", testProof, testCommitmentId)
    assert(CommittedLineDAO.insert(secondLine).get != None)
    val posibleLines = List(testLine, secondLine)
    val result = CommittedLineDAO.findByCommitment(testCommitment)
    assert(result.count == 2)
    for (commLine <- result){
      assert(posibleLines.contains(commLine))
    }
  }
  test("Method findCommitmentFromLine should return the commitment the specified line references"){
    val result = CommittedLineDAO.findCommitmentFromLine(testLine)
    assert(result != None)
    assert(result.get == testCommitment)
  }
  test("Method findCommitmentFromLineId should return the commitment the specified line id references"){
    val result = CommittedLineDAO.findCommitmentFromLineId(testLineId)
    assert(result != None)
    assert(result.get == testCommitment)
  }
  test("Method findCommitmentFromLine should return None when it can't find the commitment"){
    val secondLine = new CommittedLine("test2", testProof, new ObjectId())
    val result = CommittedLineDAO.findCommitmentFromLine(secondLine)
    assert(result == None)
  }
  test("Method findCommitmentFromLineId should return None when it can't find the commitment"){
    val secondLineId = new ObjectId()
    val result = CommittedLineDAO.findCommitmentFromLineId(secondLineId)
    assert(result == None)
  }


}
