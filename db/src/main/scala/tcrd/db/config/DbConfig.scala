package tcrd.db.config

import better.files.File

import scala.io.{ Codec, Source }

case class DbConfig(tableName: String, dbPrimaryKeyColName: String, apiKeyColName: String, file: File) {
  def source: Source = Source.fromFile(file.toJava)(Codec.UTF8)
}

object DbConfig {
  val localFile: DbConfig = {
    val tableName = "data"
    val primaryKeyColName = "TCRD ID"
    val apiKeyColName = "NCBI Gene ID"
    val file = File("tcrd/TCRDv5.4.2exp.csv")
    DbConfig(tableName, primaryKeyColName, apiKeyColName, file)
  }
}
