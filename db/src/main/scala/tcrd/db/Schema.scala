package tcrd.db

import tcrd.db.Schema.{ ColBase, VarCharCol }

import scala.util.Try

case class Schema(geneIdCol: VarCharCol, colList: Seq[ColBase], colMap: Map[String, ColBase]) {

}

object Schema {

  def apply(geneIdColName: String, cols: Set[ColBase], headers: Seq[String]): Either[String, Schema] = {
    val colMapInitial = cols.map(col => (col.name, col)).toMap
    val colList = headers.map { header =>
      colMapInitial.get(header) match {
        case Some(col) =>
          println(col); col
        case None => VarCharCol(header)
      }
    }
    val colMap = colList.map(col => (col.name, col)).toMap
    colMap.get(geneIdColName) match {
      case Some(geneIdCol: VarCharCol) =>
        Right(Schema(geneIdCol, colList, colMap))
      case Some(geneIdCol) =>
        Left(s"Column for gene id must be declared as a VARCHAR column. but is ${geneIdCol.getClass.getCanonicalName}.")
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

  sealed trait StringCol extends Col[String] {
    override def extract(string: String): String = string

    override def parse(string: String): Some[String] = Some(string)
  }

  case class VarCharCol(name: String, length: Int = 255) extends StringCol {
    override def typeName: String = s"VARCHAR($length)"
  }

  case class ClobCol(name: String) extends StringCol {
    override def typeName: String = "CLOB"
  }

  case class NumberCol(name: String) extends Col[Double] {
    override def typeName: String = "FLOAT"

    override def extract(string: String): Double = string.toDouble
  }

}
