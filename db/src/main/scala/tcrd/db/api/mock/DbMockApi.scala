package tcrd.db.api.mock

import tcrd.db.api.DbApi
import tcrd.model.{ FilterOptions, GenesFilterQuery, Status }

object DbMockApi extends DbApi {
  override def status: Status = Status.initializing("Warming up forever")

  override def possibleFilters: Seq[FilterOptions] =
    Seq(
      FilterOptions("age", List("==", "<", ">")),
      FilterOptions("name", List("==")))

  override def filterGenes(query: GenesFilterQuery): Seq[String] =
    query.genes.take(2)
}
