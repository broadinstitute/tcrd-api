package tcrd.db

import scalikejdbc.{ ConnectionPool, DB, DBSession, GlobalSettings, LoggingSQLAndTimeSettings }
import tcrd.data.DataCols
import tcrd.db.api.DbApi
import tcrd.db.config.DbConfig
import tcrd.db.load.DataLoader
import tcrd.model.{ FilterOptions, GenesFilterQuery, Status }

class DbDefaultApi(val schema: Schema, recordIter: Iterator[Either[String, Record]]) extends DbApi {
  override def status: Status = ???

  override def possibleFilters: Seq[FilterOptions] = ???

  override def filterGenes(query: GenesFilterQuery): Seq[String] = ???
}

object DbDefaultApi {

  def adjustGlobalSettings: Unit = {
    GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
      enabled = true,
      singleLineMode = false,
      printUnprocessedStackTrace = false,
      stackTraceDepth = 15,
      logLevel = 'debug,
      warningEnabled = false,
      warningThresholdMillis = 3000L,
      warningLogLevel = 'warn)
    GlobalSettings.loggingConnections = true
    GlobalSettings.loggingSQLErrors = true
  }

  def apply(config: DbConfig): Either[String, DbDefaultApi] = {
    adjustGlobalSettings
    DataLoader.getRecords(config.source, DataCols.primaryKey.name, DataCols.cols) match {
      case DataLoader.LoadFailure(message) => Left(message)
      case DataLoader.LoadSuccess(schema, recordIter) =>
        Class.forName("org.h2.Driver")
        ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")
        val sql = SqlUtils.getCreateTableSql(config.tableName, schema)
        DB.localTx { implicit session: DBSession =>
          sql.execute.apply()
        }
        println("Now inserting records")
        var recordCount: Long = 0
        var reportedAtRecordCount: Long = 0
        recordIter.foreach {
          case Left(message) =>
            println(message)
          case Right(record) =>
            val sql = SqlUtils.getInsertRecordSql(config.tableName, record)
            DB.localTx { implicit session: DBSession =>
              sql.execute.apply()
            }
            recordCount += 1
            if (recordCount > reportedAtRecordCount + reportedAtRecordCount / 11) {
              println(s"Still inserting records - inserted $recordCount records.")
              reportedAtRecordCount = recordCount
            }
        }
        println(s"Done inserting records - inserted $recordCount records.")
        Right(new DbDefaultApi(schema, recordIter))
    }
  }
}
