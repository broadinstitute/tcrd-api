package apimodels;

import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Filter
 */
@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Filter   {
  @JsonProperty("field")
  private String field = null;

  @JsonProperty("op")
  private String op = null;

  @JsonProperty("value")
  private String value = null;

  public Filter field(String field) {
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

  public Filter op(String op) {
    this.op = op;
    return this;
  }

   /**
   * Get op
   * @return op
  **/
  @NotNull
  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public Filter value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @NotNull
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Filter filter = (Filter) o;
    return Objects.equals(field, filter.field) &&
        Objects.equals(op, filter.op) &&
        Objects.equals(value, filter.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, op, value);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Filter {\n");
    
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    op: ").append(toIndentedString(op)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

