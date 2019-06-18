package tcrd.db.load

import better.files.File
import org.scalatest.FunSuite

class DataLoaderTest extends FunSuite{
  test("getRecords") {
    val dataFile = File.newTemporaryFile()
    dataFile.write(
      """
        |count, greeting, stuff
        |1, yo, "g"
        |2, hello, hello
        |3, hi, "This ""thing"", over there"
      """.stripMargin)
    dataFile.


  }
}
