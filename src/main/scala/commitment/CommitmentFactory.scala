package main.scala.commitment

/**
 * Created by fquintanilla on 14-01-15.
 */
trait CommitmentFactory {
  def commit(value : String) : Commitment
}
