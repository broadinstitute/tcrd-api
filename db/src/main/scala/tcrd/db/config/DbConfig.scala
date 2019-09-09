package tcrd.db.config

import better.files.File

import scala.io.{ Codec, Source }

case class DbConfig(tableName: String, primaryKeyColName: String, file: File) {
  def source: Source = Source.fromFile(file.toJava)(Codec.UTF8)
}

object DbConfig {
  val localFile: DbConfig = {
    val tableName = "data"
    val primaryKeyColName = "Name"
    val file = File("tcrd/TCRDv5.4.2exp.csv")
    DbConfig(tableName, primaryKeyColName, file)
  }
}
