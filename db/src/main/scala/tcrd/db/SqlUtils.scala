package tcrd.db

import scalikejdbc._
import tcrd.db.Schema.{ NumberCol, StringCol }

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
        s"$colName VARCHAR(${schema.geneIdCol.length}) NOT NULL PRIMARY KEY"
      case stringCol: StringCol =>
        val colName = quoteId(stringCol.name)
        s"$colName VARCHAR(${stringCol.length})"
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
        record.schema.colMap.getOrElse(key, StringCol(key)) match {
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

}
