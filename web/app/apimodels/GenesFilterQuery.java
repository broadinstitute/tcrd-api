package apimodels;

import apimodels.Filter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * GenesFilterQuery
 */
@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class GenesFilterQuery   {
  @JsonProperty("genes")
  private List<String> genes = new ArrayList<>();

  @JsonProperty("filters")
  private List<Filter> filters = new ArrayList<>();

  public GenesFilterQuery genes(List<String> genes) {
    this.genes = genes;
    return this;
  }

  public GenesFilterQuery addGenesItem(String genesItem) {
    genes.add(genesItem);
    return this;
  }

   /**
   * Get genes
   * @return genes
  **/
  @NotNull
  public List<String> getGenes() {
    return genes;
  }

  public void setGenes(List<String> genes) {
    this.genes = genes;
  }

  public GenesFilterQuery filters(List<Filter> filters) {
    this.filters = filters;
    return this;
  }

  public GenesFilterQuery addFiltersItem(Filter filtersItem) {
    filters.add(filtersItem);
    return this;
  }

   /**
   * Get filters
   * @return filters
  **/
  @NotNull
@Valid
  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenesFilterQuery genesFilterQuery = (GenesFilterQuery) o;
    return Objects.equals(genes, genesFilterQuery.genes) &&
        Objects.equals(filters, genesFilterQuery.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(genes, filters);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenesFilterQuery {\n");
    
    sb.append("    genes: ").append(toIndentedString(genes)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

