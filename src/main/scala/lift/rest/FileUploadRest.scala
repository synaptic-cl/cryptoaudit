package main.scala.lift.rest

import java.math.BigInteger

import main.scala.SecureBroadcastChannel.BlockchainPublisher
import main.scala.commitment.Commitment
import main.scala.filecommitment.StringArrayCommitment
import main.scala.persistance.{CommittedLineDAO, CommittedLine, TransactionDAO, Transaction}
import net.liftweb.common.{Empty, Box, Full}
import net.liftweb.http.{S,Req}
import net.liftweb.http.rest.RestHelper
import net.liftweb.json._

import scala.io.Source

/**
 * Created by fquintanilla on 26-01-15.
 */
object FileUploadRest extends RestHelper{

  case class FileUploadResponse(val tx_id : String)
  /**
   * Serve the specified URL
   *
   * e.g. case "hello" :: "world" :: Nil Get _ => ...
   * will respond to a GET request on the http://<host>/hello/world/ URL
   * */
  serve{
    // /api/file/
    case "api" :: "file" :: Nil Post req =>
      for {
        content  <- getUploadedFileAsString(req) ?~ "You forgot to upload a file"
        response <- processFile(content) ?~ "The file couldn't be processed"
      }yield Extraction.decompose(response)
  }

  private def getUploadedFileAsString(req : Req) : Box[String] = {
    val files = req.uploadedFiles
    if (files.length == 0) return Empty
    val fileStream = files(0).fileStream
    val content : String = Source.fromInputStream(fileStream).mkString
    Full(content)
  }

  private def processFile(file : String) : Box[FileUploadResponse] = {
    val lines = file.split("\n")
    val comm : StringArrayCommitment = new StringArrayCommitment(lines)
    // TODO: Calcular BitcoinTransaction
    /* replace '1' for the private key from configuration
    val pKey : BigInteger = new BigInteger("1")
    val publisher = new BlockchainPublisher(pKey, comm.commitment)
    val tx_hash = publisher.publish()
    */
    val tx_hash = comm.commitment
    val commitment = new Commitment(comm.root, comm.random)
    val tx = new Transaction(tx_hash, commitment)
    val txId = TransactionDAO.insert(tx)
    if (txId == None) return Empty
    val proofs = comm.proofArray
    for ((line, proof) <- proofs){
      val commLine = new CommittedLine(line,proof,txId.get)
      CommittedLineDAO.insert(commLine)
    }

    //    Full(FileUploadResponse(lines.length.toString))
    Full(FileUploadResponse(txId.get.toString))
  }

}
