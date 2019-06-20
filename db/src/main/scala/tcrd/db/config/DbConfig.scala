package tcrd.db.config

import better.files.File

import scala.io.{ Codec, Source }

case class DbConfig(tableName: String, source: Source)

object DbConfig {
  val localFile: DbConfig = {
    val tableName = "data"
    val file = File("/home/BROAD.MIT.EDU/oliverr/translator/tcrd/TCRDv5.4.2exp.csv")
    val source = Source.fromFile(file.toJava)(Codec.UTF8)
    DbConfig(tableName, source)
  }
}
