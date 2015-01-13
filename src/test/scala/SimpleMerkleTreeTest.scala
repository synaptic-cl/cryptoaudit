package test.scala

import main.scala.hash.SHA256Hash
import main.scala.merkletree.{SimpleMerkleNode, MerkleTree}
import org.scalatest.FunSuite

/**
 * Created by fquintanilla on 13-01-15.
 */
class SimpleMerkleTreeTest extends FunSuite{

  test("Checking correct tree structure for a known sequence"){
    val seq = Array("a", "b", "c", "d")
    /*expected values were calculated mannually using online SHA256 calculator*/
    val expectedRoot = "58c89d709329eb37285837b042ab6ff72c7c8f74de0446b091b6a0131c102cfd"
    val expectedLevels : Array[Array[String]] =
      Array(
        Array("58c89d709329eb37285837b042ab6ff72c7c8f74de0446b091b6a0131c102cfd"),
        Array("62af5c3cb8da3e4f25061e829ebeea5c7513c54949115b1acc225930a90154da",
              "d3a0f1c792ccf7f1708d5422696263e35755a86917ea76ef9242bd4a8cf4891a"),
        Array("ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb",
              "3e23e8160039594a33894f6564e1b1348bbd7a0088d42c4acb73eeaed59c009d",
              "2e7d2c03a9507ae265ecf5b5356885a53393a2029d241394997265a1a25aefc6",
              "18ac3e7343f016890c510e93f935261169d9e3f565436429830faf0934f4f8e4")
      )
    val tree = new MerkleTree[SimpleMerkleNode](seq, SHA256Hash)
    assert(tree.root == expectedRoot)
    val actualLevels = tree.getLevels
    assert(expectedLevels.deep == actualLevels.deep)
  }

  test("The leaves should be filled with '-' instead of having an inbalanced tree"){
    val seq = Array("a", "b", "c")
    /*expected values considering a balanced tree with string '-' for filler*/
    val expectedRoot = "bd82c84f46a25f93e63eca1b4748d822b640dfb841b25a988861005dc2366c6e"
    val expectedLevels =
      Array(Array("bd82c84f46a25f93e63eca1b4748d822b640dfb841b25a988861005dc2366c6e"),
            Array("62af5c3cb8da3e4f25061e829ebeea5c7513c54949115b1acc225930a90154da",
                  "527c3eb97b2106d3bc7e54e7e2662f10df7bfac5db63601408461fbfebe5c850"),
            Array("ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb",
                  "3e23e8160039594a33894f6564e1b1348bbd7a0088d42c4acb73eeaed59c009d",
                  "2e7d2c03a9507ae265ecf5b5356885a53393a2029d241394997265a1a25aefc6",
                  "3973e022e93220f9212c18d0d0c543ae7c309e46640da93a4a0314de999f5112")
      )
    val tree = new MerkleTree[SimpleMerkleNode](seq, SHA256Hash)
    assert(tree.root == expectedRoot)
    val actualLevels = tree.getLevels
    assert(expectedLevels.deep == actualLevels.deep)
  }


}
