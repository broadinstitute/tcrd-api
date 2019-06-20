package tcrd.db

import scalikejdbc._
import tcrd.db.Schema.{ NumberCol, StringCol }

object SqlUtils {

  private val dQuote: Char = '"'

  def quote(string: String): String = {
    dQuote + string.flatMap {
      case SqlUtils.dQuote => Seq(dQuote, dQuote)
      case char: Char => Seq(char)
    } + dQuote
  }

  def getCreateTableSql(tableName: String, schema: Schema): SQL[Nothing, NoExtractor] = {
    val colDefs = schema.colList.map {
      case schema.geneIdCol =>
        val colName = quote(schema.geneIdCol.name)
        s"$colName VARCHAR(255) NOT NULL PRIMARY KEY"
      case stringCol: StringCol =>
        val colName = quote(stringCol.name)
        s"$colName VARCHAR(255)"
      case numberCol: NumberCol =>
        val colName = quote(numberCol.name)
        s"$colName FLOAT"
    }
    val tableNameQuoted = SQLSyntax.createUnsafely(quote(tableName))
    val nl = SQLSyntax.createUnsafely("\n")
    val colDefsBlock = SQLSyntax.createUnsafely(colDefs.mkString(",\n  "))
    sql"CREATE TABLE ${tableNameQuoted} ($nl  ${colDefsBlock}$nl)"
  }

}
