package tcrd.db

import scalikejdbc._
import tcrd.db.Schema.{ NumberCol, StringCol, VarCharCol }

import scala.util.Try

object SqlUtils {

  private val sQuote: Char = '\''
  private val dQuote: Char = '"'

  def quoteAny(string: String, quote: Char): String = {
    quote + string.flatMap { char =>
      if (char == quote) Seq(quote, quote) else Seq(char)
    } + quote
  }

  def quoteString(string: String): String = quoteAny(string, sQuote)

  def quoteId(string: String): String = quoteAny(string, dQuote)

  def getCreateTableSql(tableName: String, schema: Schema): SQL[Nothing, NoExtractor] = {
    val colDefs = schema.colList.map {
      case schema.geneIdCol =>
        val colName = quoteId(schema.geneIdCol.name)
        schema.geneIdCol match {
          case geneIdVarCharCol: VarCharCol =>
            s"$colName VARCHAR(${geneIdVarCharCol.length}) NOT NULL PRIMARY KEY"
          case _: NumberCol =>
            // We are assuming here that no one would specify a non-integer numerical column to be primary key
            s"$colName INT NOT NULL PRIMARY KEY"
        }
      case stringCol: StringCol =>
        val colName = quoteId(stringCol.name)
        s"$colName ${stringCol.typeName}"
      case numberCol: NumberCol =>
        val colName = quoteId(numberCol.name)
        s"$colName FLOAT"
    }
    val tableNameQuoted = quoteId(tableName)
    val colDefsBlock = colDefs.mkString(",\n  ")
    SQL(s"CREATE TABLE ${tableNameQuoted} (\n  ${colDefsBlock}\n);")
  }

  def getInsertRecordSql(tableName: String, record: Record): SQL[Nothing, NoExtractor] = {
    val sqlKeyValuePairs = record.values.flatMap {
      case (key, value) =>
        record.schema.colMap.getOrElse(key, VarCharCol(key)) match {
          case _: StringCol => Some((quoteId(key), quoteString(value)))
          case _: NumberCol =>
            Try(value.toDouble).toOption match {
              case Some(_) => Some((quoteId(key), value))
              case None => None
            }

        }
    }.toList
    val tableNameQuoted = quoteId(tableName)
    val sqlKeysPhrase = "(\n  " + sqlKeyValuePairs.map(_._1).mkString(",\n  ") + "\n)"
    val sqlValuesPhrase = "(\n  " + sqlKeyValuePairs.map(_._2).mkString(",\n  ") + "\n)"
    SQL(s"INSERT INTO $tableNameQuoted $sqlKeysPhrase VALUES $sqlValuesPhrase;")
  }

  sealed trait FilterClause {
    def field: String

    def op: String

    def value: Any

    def asString: String
  }

  case class StringFilterClause(field: String, op: String, value: String) extends FilterClause {
    override def asString: String = {
      val quotedField = quoteId(field)
      val quotedValue = quoteString(value)
      s"$quotedField $op $quotedValue"
    }
  }

  case class NumberFilterClause(field: String, op: String, value: Double) extends FilterClause {
    override def asString: String = {
      val quotedField = quoteId(field)
      s"$quotedField $op $value"
    }
  }

  def getSelectGenesWhere(tableName: String, geneColName: String,
    filterClauses: Seq[FilterClause]): SQL[Nothing, NoExtractor] = {
    val selectClause = s"SELECT $geneColName FROM $tableName \n"
    val whereClause = "WHERE " + filterClauses.map(_.asString).mkString(" AND \n") + ";"
    SQL(selectClause + whereClause)
  }

}
