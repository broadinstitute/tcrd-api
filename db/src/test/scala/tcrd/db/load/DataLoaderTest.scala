package tcrd.db.load

import better.files.File
import org.scalatest.FunSuite
import tcrd.db.Schema.ColBase

class DataLoaderTest extends FunSuite {
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
    DataLoader.getRecords(dataFile, "id", Set.empty[ColBase]) match {
      case DataLoader.LoadFailure(message) => println(message)
      case DataLoader.LoadSuccess(schema, recordIterator) =>
        println(schema)
        recordIterator.foreach(println)
    }
  }
}
