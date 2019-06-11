package tcrd_api.server.model


/**
 * @param genes 
 * @param filters 
 */
case class Body (
  genes: List[String],
  filters: List[filter_filters]
)

