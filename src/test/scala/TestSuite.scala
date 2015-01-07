
/**
 * Created by fquintanilla on 06-01-15.
 */
package test.scala

import org.scalatest.FunSuite

class TestSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}