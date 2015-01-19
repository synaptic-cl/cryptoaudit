package commitment

/**
 * Created by fquintanilla on 14-01-15.
 */
trait Commitment {
  def commit(value : String) : (String,String)
}
