package tcrd.db.load

import tcrd.db.Schema.ColBase
import tcrd.db.{Record, Schema}

import scala.io.Source

object DataLoader {

  def getRecords(source: Source, geneIdColName: String, cols: Set[ColBase]):
  Either[String, Iterator[Either[String, Record]] = {
    val lineIter = source.getLines().zipWithIndex
    if (!lineIter.hasNext) {
      Right(Iterator.empty)
    } else {
      val (headerLine, _) = lineIter.next()
      CsvLineParser.parseLine(headerLine) match {
        case Right(headers) =>
          Schema(geneIdColName, cols, headers) match {
            case Right(schema) =>
              val recordIter = lineIter.map { case (line, index) =>
                CsvLineParser.parseLine(line) match {
                  case Right(values) =>
                    val nValues = values.size
                    if(values.size != schema.colList.size) {
                      Left(s"Row $index has ${values.size} values but needs to have ${schema.colList.size}")
                    } else {
                      val valueMap = schema.colList.map(_.name).zip(values).toMap
                      val geneId = valueMap(schema.geneIdCol.name)
                      Right(Record(geneId, valueMap, schema))
                    }
                }
              }
            case Left(message) => Left(message)
          }
        case Left(message) => Left(message)
      }
    }
  }

}
