package hello.itemservice2.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("hello.itemservice2.exception")
public class ExControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorResult illegalExHandle(IllegalArgumentException e) {
    log.error("[exceptionHandle] ex", e);
    return new ErrorResult("BAD", e.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResult> exHandle(Exception e) {
    log.error("[exceptionHandle] ex", e);
    ErrorResult errorResult = new ErrorResult("EX", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
