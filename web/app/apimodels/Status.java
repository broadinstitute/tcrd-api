package apimodels;

import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Status
 */
@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Status   {
  /**
   * Gets or Sets id
   */
  public enum IdEnum {
    INITIALIZING("initializing"),
    
    READY("ready"),
    
    FAILED("failed");

    private final String value;

    IdEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static IdEnum fromValue(String text) {
      for (IdEnum b : IdEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("id")
  private IdEnum id = null;

  @JsonProperty("message")
  private String message = null;

  public Status id(IdEnum id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @NotNull
  public IdEnum getId() {
    return id;
  }

  public void setId(IdEnum id) {
    this.id = id;
  }

  public Status message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @NotNull
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Status status = (Status) o;
    return Objects.equals(id, status.id) &&
        Objects.equals(message, status.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Status {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

