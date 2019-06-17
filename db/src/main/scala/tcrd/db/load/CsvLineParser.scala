package tcrd.db.load

object CsvLineParser {

  case class ValueAndRemainder(value: String, remainder: String, hasMore: Boolean)

  private def consumeBareValue(remainder: String): Either[String, ValueAndRemainder] = {
    val commaIndex = remainder.indexOf(',')
    if (commaIndex < 0) {
      Right(ValueAndRemainder(remainder, "", false))
    } else {
      Right(ValueAndRemainder(remainder.substring(0, commaIndex), remainder.substring(commaIndex + 1), true))
    }
  }

  private def consumeQuotedValue(remainder: String): Either[String, ValueAndRemainder] = {
    var value: String = ""
    var openQuoteIndex: Int = 0
    var errorOpt: Option[String] = None
    var valueAndRemainderOpt: Option[ValueAndRemainder] = None
    while (errorOpt.isEmpty && valueAndRemainderOpt.isEmpty) {
      val closeQuoteIndex: Int = remainder.indexOf('"', openQuoteIndex + 1)
      if (closeQuoteIndex < 0) {
        errorOpt = Some(s"No closing quote for opening quote at index $openQuoteIndex.")
      } else {
        value += remainder.substring(openQuoteIndex + 1, closeQuoteIndex)
        val nextIndex = closeQuoteIndex + 1
        if (nextIndex >= remainder.size) {
          valueAndRemainderOpt = Some(ValueAndRemainder(value, "", false))
        } else {
          remainder.charAt(nextIndex) match {
            case ',' =>
              valueAndRemainderOpt = Some(ValueAndRemainder(value, remainder.substring(nextIndex + 1), true))
            case '"' =>
              value += '"'
              openQuoteIndex = nextIndex
            case char =>
              errorOpt = Some(s"""Expected ',' or '"' at index $nextIndex, but found $char.""")
          }
        }
      }
    }
    (errorOpt, valueAndRemainderOpt) match {
      case (Some(error), _) => Left(error)
      case (_, Some(valueAndRemainder)) => Right(valueAndRemainder)
      case (_, _) => Left("Must be some bug in the code.")
    }
  }

  def parseLine(line: String): Either[String, Seq[String]] = {
    var values: Seq[String] = Seq.empty
    var remainder: String = line
    var errorOpt: Option[String] = None
    var hasMore: Boolean = true
    while (errorOpt.isEmpty && hasMore) {
      val valueAndRemainderEither =
        if (remainder.isEmpty) {
          Right(ValueAndRemainder("", "", false))
        } else {
          if (remainder.charAt(0) == '"') {
            consumeQuotedValue(remainder)
          } else {
            consumeBareValue(remainder)
          }
        }
      valueAndRemainderEither match {
        case Right(valueAndRemainder) =>
          values :+= valueAndRemainder.value
          remainder = valueAndRemainder.remainder
          hasMore = valueAndRemainder.hasMore
        case Left(errorNew) => errorOpt = Some(errorNew)
      }
    }
    errorOpt match {
      case Some(error) => Left(error)
      case None => Right(values)
    }
  }

}
