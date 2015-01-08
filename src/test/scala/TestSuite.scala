
/**
 * Created by fquintanilla on 06-01-15.
 */
package test.scala

import org.scalatest.FunSuite
import main.scala.HolaMundo._

class TestSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }

  test("Use our SHA1 function defined in HolaMundo") {
    var message: String = "test"
    var firstHash = mySha1(message)
    var secondHash = mySha1(message)
    assert(firstHash.deep == secondHash.deep)
  }
}