package tcrd.db

import tcrd.db.Schema.{ Col, ColBase, StringCol }

import scala.util.Try

case class Schema(geneIdCol: StringCol, colList: Seq[ColBase], colMap: Map[String, ColBase]) {

}

object Schema {

  def apply(geneIdColName: String, cols: Set[ColBase], headers: Seq[String]): Either[String, Schema] = {
    val colMapInitial = cols.map(col => (col.name, col)).toMap
    val colList = headers.map { header =>
      colMapInitial.get(header) match {
        case Some(col) => col
        case None => StringCol(header)
      }
    }
    val colMap = colList.map(col => (col.name, col)).toMap
    colMap.get(geneIdColName) match {
      case Some(geneIdCol: StringCol) =>
        Right(Schema(geneIdCol, colList, colMap))
      case Some(geneIdCol) =>
        Left(s"Column for gene id must be declared as String column. but is ${geneIdCol.getClass.getCanonicalName}.")
      case None =>
        Left(s"Data is missing column $geneIdColName.")
    }
  }

  sealed trait ColBase {
    def name: String

    def typeName: String

    def asString: String = s"$typeName($name)"

    def extract(string: String): Any

    def parse(string: String): Option[Any] = Try {
      extract(string)
    }.toOption
  }

  sealed trait Col[T] extends ColBase {
    def name: String

    def typeName: String

    def extract(string: String): T

    override def parse(string: String): Option[T] = Try {
      extract(string)
    }.toOption
  }

  case class StringCol(name: String) extends Col[String] {
    override def typeName: String = "String"

    override def extract(string: String): String = string

    override def parse(string: String): Some[String] = Some(string)
  }

  case class NumberCol(name: String) extends Col[Double] {
    override def typeName: String = "Number"

    override def extract(string: String): Double = string.toDouble
  }

}
