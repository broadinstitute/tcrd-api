package tcrd.data

import tcrd.db.Schema.{ ClobCol, ColBase, VarCharCol }

object DataCols {

  val primaryKey: VarCharCol = VarCharCol("Name")

  val cols: Set[ColBase] = Set[ColBase](
    ClobCol("ChEMBL Selective Compound"),
    ClobCol("Drugable Epigenome Class(es)"),
    ClobCol("eRAM Diseases"),
    ClobCol("Experimental MF/BP Leaf Term GOA(s)"),
    ClobCol("GWAS Phenotype(s)"),
    ClobCol("IMPC Phenotype(s)"),
    ClobCol("JAX/MGI Human Ortholog Phenotype(s)"),
    ClobCol("OMIM Confirmed Phenotype(s)"),
    ClobCol("PANTHER Class(es)"),
    ClobCol("Pathways"),
    ClobCol("PDBs"),
    ClobCol("Top 5 Text-Mining DISEASES"))

}
