package test.scala

import main.scala.hash.SHA256Hash._
import org.scalatest.FunSuite
/**
 * Created by fquintanilla on 09-01-15.
 */

class SHA256HashTest extends FunSuite{

  test("Calculating the hash twice for the same input should give the same result"){
    val text : String = "test"
    val firstResult = calculateHash(text)
    val secondResult = calculateHash(text)
    assert(firstResult == secondResult)
  }

  test("Our SHA256 function should return the expected checksum for a known text") {
    val text: String = "test"
    // expectedResult obtained with the shasum command on a linux terminal
    val expectedResult = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"
    var actualResult = calculateHash(text)
    assert(expectedResult == actualResult)
  }

}
