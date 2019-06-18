package tcrd.db.load

import better.files.File
import org.scalatest.FunSuite
import tcrd.db.{ Record, Schema }
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
        |id,count,greeting,stuff
        |gene1,1,yo,"g"
        |gene2,2,hello,hello
        |gene3,3,hi,"This ""thing"", over there"
        |not,enough,cols
        |a,bit,too,many,cols
      """.stripMargin.trim)
    val countCol = NumberCol("count")
    DataLoader.getRecords(dataFile, "id", Set[ColBase](countCol)) match {
      case DataLoader.LoadFailure(message) => fail(message)
      case DataLoader.LoadSuccess(schema, recordIterator) =>
        val geneIdCol = StringCol("id")
        assertSchema(schema, geneIdCol, countCol)
        val recordEitherList = recordIterator.toList
        assert(recordEitherList.size == 5)
        assert(recordEitherList(0) ==
          Right(Record("gene1", Map("id" -> "gene1", "count" -> "1", "greeting" -> "yo", "stuff" -> "g"), schema)))
        assert(recordEitherList(1) ==
          Right(Record(
            "gene2",
            Map("id" -> "gene2", "count" -> "2", "greeting" -> "hello", "stuff" -> "hello"), schema)))
        assert(recordEitherList(2) ==
          Right(Record(
            "gene3",
            Map("id" -> "gene3", "count" -> "3", "greeting" -> "hi", "stuff" -> "This \"thing\", over there"),
            schema)))
        assert(recordEitherList(3) == Left("Row 4 has 3 values but needs to have 4"))
        assert(recordEitherList(4) == Left("Row 5 has 5 values but needs to have 4"))
    }
  }
}
