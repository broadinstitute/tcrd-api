package tcrd.db.load

import org.scalatest.FunSuite

class CsvLineParserTest extends FunSuite {
  test("parseLine") {
    assert(CsvLineParser.parseLine("apple, pear,cherry ") === Right(Seq("apple", " pear", "cherry ")))
    assert(CsvLineParser.parseLine("\"This \"\"one\"\" is quoted\",\"and this\"") ===
      Right(Seq("This \"one\" is quoted", "and this")))
    assert(CsvLineParser.parseLine("a,\"This has a \"\" and a , in it\",b") ===
      Right(Seq("a", "This has a \" and a , in it", "b")))
    assert(CsvLineParser.parseLine("a,,b,,,b,") === Right(Seq("a", "", "b", "", "", "b", "")))
  }
}
