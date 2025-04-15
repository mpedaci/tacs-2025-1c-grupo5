package utn.tacs.grupo5.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

  public static <T> ResponseEntity<T> generateResponseOK(T body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  public static ResponseEntity<CustomError> generateResponseError(
      HttpStatus status, String message, String detail) {
    CustomError customError = new CustomError(message, detail);
    return ResponseEntity.status(status).body(customError);
  }

  public static ResponseEntity<CustomError> generateResponseError(
      HttpStatus status, String message) {
    return generateResponseError(status, message, null);
  }
}
