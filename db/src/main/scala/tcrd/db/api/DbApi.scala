package tcrd.db.api

import tcrd.model.{ FilterOptions, GenesFilterQuery, Status }

trait DbApi {

  def status: Status
  def possibleFilters: Seq[FilterOptions]
  def filterGenes(query: GenesFilterQuery): Seq[String]

}
