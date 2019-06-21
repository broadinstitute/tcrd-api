package tcrd.data

import tcrd.data.DataSurveyor.DataPoll
import tcrd.db.Schema.{ ClobCol, ColBase, NumberCol, VarCharCol }

object DataColGenerator {

  def generateCols(dataPoll: DataPoll): Map[String, ColBase] = {
    dataPoll.fields.map {
      case (name, fieldPoll) =>
        if (fieldPoll.couldBeNumberCount.isUnanimouslyYes) {
          (name, NumberCol(name))
        } else {
          if (fieldPoll.maxLength <= 700) {
            (name, VarCharCol(name, Math.max(1, fieldPoll.maxLength)))
          } else {
            (name, ClobCol(name))
          }
        }
    }
  }
}
