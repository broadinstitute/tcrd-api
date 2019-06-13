package apimodels;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * FilterOptions
 */
@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class FilterOptions   {
  @JsonProperty("field")
  private String field = null;

  @JsonProperty("ops")
  private List<String> ops = new ArrayList<>();

  public FilterOptions field(String field) {
    this.field = field;
    return this;
  }

   /**
   * Get field
   * @return field
  **/
  @NotNull
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public FilterOptions ops(List<String> ops) {
    this.ops = ops;
    return this;
  }

  public FilterOptions addOpsItem(String opsItem) {
    ops.add(opsItem);
    return this;
  }

   /**
   * Get ops
   * @return ops
  **/
  @NotNull
  public List<String> getOps() {
    return ops;
  }

  public void setOps(List<String> ops) {
    this.ops = ops;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilterOptions filterOptions = (FilterOptions) o;
    return Objects.equals(field, filterOptions.field) &&
        Objects.equals(ops, filterOptions.ops);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, ops);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FilterOptions {\n");
    
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    ops: ").append(toIndentedString(ops)).append("\n");
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

