package tcrd.data

import tcrd.db.Record

import scala.util.Try

object DataSurveyor {

  case class Count(nYes: Long, nNo: Long) {
    def nTotal: Long = nYes + nNo
    def yesFraction: Double = nYes.toDouble / (nYes + nNo)
    def noFraction: Double = nNo.toDouble / (nYes + nNo)
    def isUnanimouslyYes: Boolean = nYes > 0 && nNo == 0
    def isUnanimouslyNo: Boolean = nYes == 0 && nNo > 0
    def +(oCount: Count): Count = Count(nYes + oCount.nYes, nNo + oCount.nNo)
    def asString: String = s"yes: $nYes ($yesFraction), no: $nNo ($noFraction), total: $nTotal"
  }

  object Count {
    val singleYes: Count = Count(1, 0)
    val singleNo: Count = Count(0, 1)
    def singleIf(boolean: Boolean): Count = if (boolean) singleYes else singleNo
  }

  case class FieldPoll(
    maxLength: Int,
    couldBeNumberCount: Count,
    shortNonNumberValues: Set[String]) {
    def +(o: FieldPoll): FieldPoll =
      FieldPoll(
        maxLength = Math.max(maxLength, o.maxLength),
        couldBeNumberCount = couldBeNumberCount + o.couldBeNumberCount,
        shortNonNumberValues = shortNonNumberValues ++ o.shortNonNumberValues)
    def asString: String = {
      s"""
         | maxLength: $maxLength
         | could be number: ${couldBeNumberCount.asString}
         | short non-number values: ${shortNonNumberValues.mkString(", ").take(70)}
       """.stripMargin.trim
    }
  }

  object FieldPoll {
    def couldBeNumber(string: String): Boolean = string.isEmpty || Try(string.toDouble).isSuccess

    def fromField(string: String): FieldPoll = {
      val maxLength = string.size
      val mayBeNumber = couldBeNumber(string)
      val couldBeNumberCount = Count.singleIf(mayBeNumber)
      val shortNonNumberValues = if (!mayBeNumber && string.size < 20) Set(string) else Set.empty[String]
      FieldPoll(maxLength, couldBeNumberCount, shortNonNumberValues)
    }
  }

  case class DataPoll(nRecords: Long, fields: Map[String, FieldPoll]) {
    def +(o: DataPoll): DataPoll = {
      val nRecordsNew = nRecords + o.nRecords
      val keys = fields.keySet ++ o.fields.keySet
      val fieldsNew = keys.map { key =>
        (fields.get(key), o.fields.get(key)) match {
          case (Some(fieldPoll), Some(oFieldPoll)) => (key, fieldPoll + oFieldPoll)
          case (Some(fieldPoll), None) => (key, fieldPoll)
          case (None, Some(oFieldPoll)) => (key, oFieldPoll)
          case (None, None) => throw new RuntimeException("This should never happen. Must be a bug.")
        }
      }.toMap
      DataPoll(nRecordsNew, fieldsNew)
    }
    def asString: String = {
      fields.map {
        case (key, value) => s"$key -> \n${value.asString}"
      }.mkString("\n")
    }
  }

  object DataPoll {
    val empty: DataPoll = DataPoll(0, Map.empty)
    def fromRecord(record: Record): DataPoll = {
      val fields = record.values.mapValues(FieldPoll.fromField).view.force
      DataPoll(1, fields)
    }
  }

}
