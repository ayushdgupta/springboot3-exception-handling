package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.advice;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFoundException;

import java.util.HashMap;
import java.util.Map;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
// @ControllerAdvice
public class ApplicationExceptionHandler {

  /*
     In below error response we are sending a map, we can also send any other response header, as we are using
     @RestControllerAdvice so our Map will go in the output with in the HTTP Response.
     If we were using @ControllerAdvice then we need to use @ResponseBody to get the response in our required
     format otherwise the format will be the one that spring boot uses in the corresponding case.
  */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  @ResponseBody
  public Map<String, String> handleFieldValidationError(MethodArgumentNotValidException e) {
    Map<String, String> errorMap = new HashMap<>();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(
            errorResponse ->
                errorMap.put(errorResponse.getField(), errorResponse.getDefaultMessage()));
    return errorMap;
  }

  /*
     So here we have taken 'Exception e' to receive the exception we can also use 'StudentNotFoundException ss'
     over there. In case of multiple exceptions in @ExceptionHandler we use 'Exception e' only.

     By Adding @ResponseStatus we are getting the HTTPStatus code correctly in the response
  */
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleStudentNotFoundException(Exception e) {
      ErrorMessage em = new ErrorMessage();
      em.setErrorMessage(e.getMessage());
      em.setErrorStatus(HttpStatus.NOT_FOUND);
      //    If we don't want to send error trace then comment it
      //    em.setErrorTrace(e.getStackTrace());
      return em;
    }

  /*

    Instead of sending an ErrorMessage we can also send a ResponseEntity for the 'StudentNotFoundException'.
    This also works fine.

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<?> handleStudentNotFoundException(Exception e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }
   */
}
