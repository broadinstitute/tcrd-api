package tcrd.db

import scalikejdbc.{ ConnectionPool, DB, DBSession, GlobalSettings, LoggingSQLAndTimeSettings }
import tcrd.data.DataColGenerator
import tcrd.data.DataSurveyor.DataPoll
import tcrd.db.Schema.{ NumberCol, StringCol }
import tcrd.db.api.DbApi
import tcrd.db.config.DbConfig
import tcrd.db.load.DataLoader
import tcrd.model.{ FilterOptions, GenesFilterQuery, Status }

class DbDefaultApi(val config: DbConfig, val schema: Schema, val nRecords: Long) extends DbApi {
  override def status: Status = Status.ready(s"Loaded $nRecords records with ${schema.colList.size} fields.")

  override def possibleFilters: Seq[FilterOptions] = {
    schema.colList.map {
      case NumberCol(name) => FilterOptions(name, List("=", "<", ">", "<=", ">="))
      case stringCol: StringCol => FilterOptions(stringCol.name, List("="))
    }
  }

  override def filterGenes(query: GenesFilterQuery): Seq[String] = {
    val filterClauses = query.filters.map { filter =>
      schema.colMap(filter.field) match {
        case _: NumberCol => SqlUtils.NumberFilterClause(filter.field, filter.op, filter.value.toDouble)
        case _ => SqlUtils.StringFilterClause(filter.field, filter.op, filter.value)
      }
    }
    val sql = SqlUtils.getSelectGenesWhere(config.tableName, config.primaryKeyColName, filterClauses)
    val genesFound = DB.localTx { implicit session: DBSession =>
      sql.map(rs => rs.string(config.primaryKeyColName)).list().apply()
    }.toSet
    query.genes.filter(genesFound)
  }
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

  sealed trait RecordProcessor[R] {
    def nameOfActivity: String
    def init(): Unit
    def processRecord(record: Record): Unit
    def getResult: R
  }

  class DataSurveyor extends RecordProcessor[DataPoll] {
    override def nameOfActivity: String = "surveying"

    override def init(): Unit = {}

    var dataPoll: DataPoll = DataPoll.empty

    override def processRecord(record: Record): Unit = {
      val dataPollNew = DataPoll.fromRecord(record)
      dataPoll += dataPollNew
    }

    override def getResult: DataPoll = dataPoll
  }

  class DbInserter(config: DbConfig, schema: Schema) extends RecordProcessor[DbDefaultApi] {
    override def nameOfActivity: String = "inserting"

    override def init(): Unit = {
      Class.forName("org.h2.Driver")
      ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")
      val sql = SqlUtils.getCreateTableSql(config.tableName, schema)
      DB.localTx { implicit session: DBSession =>
        sql.execute.apply()
      }
    }

    var nRecords: Long = 0

    override def processRecord(record: Record): Unit = {
      val sql = SqlUtils.getInsertRecordSql(config.tableName, record)
      DB.localTx { implicit session: DBSession =>
        sql.execute.apply()
        nRecords += 1
      }
    }

    override def getResult: DbDefaultApi = new DbDefaultApi(config, schema, nRecords)
  }

  def processRecords[R](recordIter: Iterator[Either[String, Record]], recordProcessor: RecordProcessor[R]): R = {
    recordProcessor.init()
    println(s"Now ${recordProcessor.nameOfActivity} records")
    var recordCount: Long = 0
    var reportedAtRecordCount: Long = 0
    recordIter.foreach {
      case Left(message) =>
        println(message)
      case Right(record) =>
        recordProcessor.processRecord(record)
        recordCount += 1
        if (recordCount > reportedAtRecordCount + reportedAtRecordCount / 11) {
          println(s"Still ${recordProcessor.nameOfActivity} records - processed $recordCount records.")
          reportedAtRecordCount = recordCount
        }
    }
    println(s"Done ${recordProcessor.nameOfActivity} records - processed $recordCount records.")
    recordProcessor.getResult
  }

  def apply(config: DbConfig): Either[String, DbDefaultApi] = {
    adjustGlobalSettings
    val primaryKeyColName = config.primaryKeyColName
    DataLoader.getRecords(config.source, primaryKeyColName, Set.empty) match {
      case DataLoader.LoadFailure(message) =>
        println(message)
        Left(message)
      case DataLoader.LoadSuccess(_, recordIter) =>
        val dataSurveyor = new DataSurveyor
        val dataPoll = processRecords(recordIter, dataSurveyor)
        println("data poll:")
        val colsMap = DataColGenerator.generateCols(dataPoll)
        println(colsMap)
        DataLoader.getRecords(config.source, primaryKeyColName, colsMap.values.toSet) match {
          case DataLoader.LoadFailure(message) => Left(message)
          case DataLoader.LoadSuccess(schema, recordIter) =>
            val recordInserter = new DbInserter(config, schema)
            Right(processRecords(recordIter, recordInserter))
        }
    }
  }
}
