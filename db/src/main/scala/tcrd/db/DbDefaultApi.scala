package tcrd.db

import scalikejdbc.{ConnectionPool, DB, DBSession, GlobalSettings, LoggingSQLAndTimeSettings}
import scalikejdbc._
import tcrd.db.Schema.{ColBase, NumberCol, StringCol}
import tcrd.db.api.DbApi
import tcrd.db.config.DbConfig
import tcrd.db.load.DataLoader
import tcrd.model.{FilterOptions, GenesFilterQuery, Status}

import scala.util.{Failure, Success, Try}

class DbDefaultApi(val schema: Schema, recordIter: Iterator[Either[String, Record]]) extends DbApi {
  override def status: Status = ???

  override def possibleFilters: Seq[FilterOptions] = ???

  override def filterGenes(query: GenesFilterQuery): Seq[String] = ???
}

object DbDefaultApi {

  def apply(config: DbConfig): Either[String, DbDefaultApi] = {
    GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
      enabled = true,
      singleLineMode = false,
      printUnprocessedStackTrace = true,
      stackTraceDepth = 15,
      logLevel = 'debug,
      warningEnabled = false,
      warningThresholdMillis = 3000L,
      warningLogLevel = 'warn)
    GlobalSettings.loggingConnections = true
    GlobalSettings.loggingSQLErrors = true
    val geneIdCol = StringCol(config.geneIdColName)
    val cols = config.numberColNames.map(NumberCol).toSet[ColBase] + geneIdCol
    DataLoader.getRecords(config.source, config.geneIdColName, cols) match {
      case DataLoader.LoadFailure(message) => Left(message)
      case DataLoader.LoadSuccess(schema, recordIter) =>
        Class.forName("org.h2.Driver")
        ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")
        DB.localTx { implicit session: DBSession =>
          val sql = SqlUtils.getCreateTableSql(config.tableName, schema)
          println(sql.statement)
          sql.execute.apply()
        }
        Right(new DbDefaultApi(schema, recordIter))
    }
  }
}
