package tcrd.db

import org.scalatest.FunSuite
import tcrd.db.Schema.{ ColBase, NumberCol, VarCharCol }

class SqlUtilsTest extends FunSuite {
  test("quote") {
    val string = "I just came to say \"hello\"!"
    val quotedString = "\"I just came to say \"\"hello\"\"!\""
    assert(SqlUtils.quoteId("I just came to say \"hello\"!") === quotedString)
  }
  test("getCreateTableSql") {
    val tableName = "blub"
    val geneIdColName = "cool genes"
    val someCols: Set[ColBase] = Set[ColBase](NumberCol("size"), VarCharCol(geneIdColName), VarCharCol("yo"))
    val headers = Seq("stuff", "cool genes", "yo", "blub", "size")
    val schema = Schema(geneIdColName, someCols, headers).right.get
    val sql = SqlUtils.getCreateTableSql(tableName, schema)
    val sqlStringExpected =
      """
        |CREATE TABLE "blub" (
        |  "stuff" VARCHAR(255),
        |  "cool genes" VARCHAR(255) NOT NULL PRIMARY KEY,
        |  "yo" VARCHAR(255),
        |  "blub" VARCHAR(255),
        |  "size" FLOAT
        |);
      """.stripMargin.trim
    assert(sql.statement === sqlStringExpected)
  }
}
