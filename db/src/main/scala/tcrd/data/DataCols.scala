package tcrd.data

import tcrd.db.Schema.{ ColBase, StringCol }

object DataCols {

  val primaryKey: StringCol = StringCol("Name")

  val cols: Set[ColBase] = Set[ColBase](
    StringCol("ChEMBL Selective Compound", 700),
    StringCol("Drugable Epigenome Class(es)", 700),
    StringCol("eRAM Diseases", 1800),
    StringCol("Experimental MF/BP Leaf Term GOA(s)", 3000),
    StringCol("GWAS Phenotype(s)", 15000),
    StringCol("IMPC Phenotype(s)", 40000),
    StringCol("JAX/MGI Human Ortholog Phenotype(s)", 1000),
    StringCol("OMIM Confirmed Phenotype(s)", 1000),
    StringCol("PANTHER Class(es)", 400),
    StringCol("Pathways", 5000000),
    StringCol("PDBs", 4000),
    StringCol("Top 5 Text-Mining DISEASES", 400))

}
