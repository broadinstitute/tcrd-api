package tcrd.db

import tcrd.db.Schema.NumberCol

case class Record(id: String, values: Map[String, String], schema: Schema) {

  def getString(colName: String): Option[String] = values.get(colName)

  def getNumber(colName: String): Option[Double] = {
    (values.get(colName), schema.colMap.get(colName)) match {
      case (Some(value), Some(numberCol: NumberCol)) => numberCol.parse(value)
      case _ => None
    }
  }

}
