package utn.tacs.grupo5.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Error Schema", description = "Error schema")
public class CustomError {
  @Schema(description = "error message", example = "error message")
  private String message;

  @Schema(description = "error details", example = "error details")
  private String error;
}
