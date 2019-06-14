package tcrd.model

case class GenesFilterQuery(
  genes: List[String],
  filters: List[Filter])
