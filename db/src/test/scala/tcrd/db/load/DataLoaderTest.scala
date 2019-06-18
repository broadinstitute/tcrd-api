package tcrd.db.load

import better.files.File
import org.scalatest.FunSuite
import tcrd.db.Schema
import tcrd.db.Schema.{ ColBase, NumberCol, StringCol }

class DataLoaderTest extends FunSuite {

  def assertSchema(schema: Schema, geneIdCol: StringCol, countCol: NumberCol): Unit = {
    assert(schema.colList.size == 4)
    assert(schema.geneIdCol == geneIdCol)
    assert(schema.colList(0) == geneIdCol)
    assert(schema.colMap("id") == geneIdCol)
    assert(schema.colList(1) == countCol)
    assert(schema.colMap("count") == countCol)
    assert(schema.colList(2) == schema.colMap("greeting"))
    assert(schema.colList(3) == schema.colMap("stuff"))
  }

  test("getRecords") {
    val dataFile = File.newTemporaryFile()
    dataFile.write(
      """
        |id, count, greeting, stuff
        |gene1, 1, yo, "g"
        |gene2, 2, hello, hello
        |gene3, 3, hi, "This ""thing"", over there"
        |not, enough, cols
        |a, bit, too, many, cols
      """.stripMargin.trim)
    println(dataFile.contentAsString)
    val countCol = NumberCol("count")
    DataLoader.getRecords(dataFile, "id", Set[ColBase](countCol)) match {
      case DataLoader.LoadFailure(message) => fail(message)
      case DataLoader.LoadSuccess(schema, recordIterator) =>
        val geneIdCol = StringCol("id")
        assertSchema(schema, geneIdCol, countCol)
        val recordEitherList = recordIterator.toList
        println(schema)
        recordIterator.foreach(println)
    }
  }
}
