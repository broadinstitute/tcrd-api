package tcrd_api.server.model


/**
 * @param genes 
 * @param filters 
 */
case class GenesFilterQuery (
  genes: List[String],
  filters: List[Filter]
)

