package tcrd.db

import org.scalatest.FunSuite
import tcrd.db.Schema.{ ColBase, NumberCol, StringCol }

class SqlUtilsTest extends FunSuite {
  test("quote") {
    val string = "I just came to say \"hello\"!"
    val quotedString = "\"I just came to say \"\"hello\"\"!\""
    assert(SqlUtils.quoteId("I just came to say \"hello\"!") === quotedString)
  }
  test("getCreateTableSql") {
    val tableName = "blub"
    val geneIdColName = "cool genes"
    val someCols: Set[ColBase] = Set[ColBase](NumberCol("size"), StringCol(geneIdColName), StringCol("yo"))
    val headers = Seq("stuff", "cool genes", "yo", "blub", "size")
    val schema = Schema(geneIdColName, someCols, headers).right.get
    val sql = SqlUtils.getCreateTableSql(tableName, schema)
    println(sql.statement)
  }
}
