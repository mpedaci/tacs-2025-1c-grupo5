package utn.tacs.grupo5.controller.exceptions;

import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomError> handleBadRequest(Exception e) {
    logger.info("Bad Request: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<CustomError> handleConflict(Exception e) {
    logger.info("Conflict: " + e.getMessage());
    return ResponseGenerator.generateResponseError(HttpStatus.CONFLICT, "Conflict", e.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomError> handleNotFound(Exception e) {
    logger.info("Not Found: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.NOT_FOUND, "Not Found", e.getMessage());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<CustomError> handleNonExistingEndpoint(Exception e) {
    logger.info("NotFount: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.NOT_FOUND, "Not Found");
  }

  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public ResponseEntity<CustomError> handleInternalServerError(Exception e) {
    logger.error("Internal Server Error: " + e);
    return ResponseGenerator.generateResponseError(
        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }
}
