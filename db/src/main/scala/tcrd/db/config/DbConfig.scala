package tcrd.db.config

import better.files.File

import scala.io.{ Codec, Source }

case class DbConfig(tableName: String, primaryKeyColName: String, file: File) {
  def source: Source = Source.fromFile(file.toJava)(Codec.UTF8)
}

object DbConfig {
  val localFile: DbConfig = {
    val tableName = "data"
    val primaryKeyColName = "NCBI Gene ID"
    val file = File("/home/BROAD.MIT.EDU/oliverr/translator/tcrd/TCRDv5.4.2exp.csv")
    DbConfig(tableName, primaryKeyColName, file)
  }
}
