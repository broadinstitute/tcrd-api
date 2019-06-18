package tcrd.db.load

import better.files.File
import org.scalatest.FunSuite

class DataLoaderTest extends FunSuite{
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
      """.stripMargin)



  }
}
