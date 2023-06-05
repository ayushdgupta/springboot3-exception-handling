package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.controller;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service.StudentServiceImpl;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

  Logger LOG = LogManager.getLogger(StudentController.class);

  @Autowired private StudentServiceImpl studentService;

  /*
     Case 1: If we do not add @Valid annotation below, whenever we receive any wrong data in the field like 'null' in the
     firstName then the code will goto API then to Service layer and then when it go for save the data in DB then it'll
     throw an exception (in default format followed by SpringBoot) that there is something wrong in the data.

    Case 2: If we will add @Valid annotation below, first the data will be validated then it'll be allowed to enter into
    our API and if any error will be caught during validation then an exception will be thrown but this time whatever
    the fields violating the constraints all will be received in error and the exception format will also be different
    i.e. not the default one.

    In above method the exception message is coming from spring boot we are not doing anything wih the exception header.
    but What If we need to change the exception header like suppose if we want to send a map where key contains field
    name and the value will be the error response only or may be to some other response header according to our
    requirement then in that case we need to use @RestControllerAdvice + @ExceptionHandler.
    @RestControllerAdvice = @ControllerAdvice + @ResponseBody.

    M3-> Handle exception using @RestControllerAdvice
  */
  @PostMapping
  public ResponseEntity<?> insertNewStudent(@RequestBody @Valid Student student) {
    LOG.info("Hit insertNewStudent API");
    Student studentSavedInstance = studentService.addNewStudentData(student);
    LOG.info("Save the data in db");
    return new ResponseEntity<>(studentSavedInstance, HttpStatus.CREATED);
  }

  // M1-> Default Spring Boot exception handling
  @GetMapping("/{rollNo}")
  public ResponseEntity<?> getStudentByRollNo(@PathVariable("rollNo") int roll) {
    LOG.info("Hit getStudentByRollNo API");
    Student studentData = studentService.getStudentByRollDefaultExceptionHandling(roll);
    LOG.info("Found the data for the id {}", roll);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }

  // M2-> Using @ResponseStatus to a custom defined exception
  // In Below API we are able to customize the Response status as well and the server.error
  // properties are still in play
  @GetMapping("/customExceptionWithResponseStatus/{rollNo}")
  public ResponseEntity<?> getStudentByRollNoWithCustomException(@PathVariable("rollNo") int roll) {
    LOG.info("Hit getStudentByRollNo API");
    Student studentData = studentService.getStudentByRollUsingCustomExceptionHandling(roll);
    LOG.info("Found the data for the id {}", roll);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }

  // M3-> Here we are using @RestControllerAdvice and @ExceptionHandler. In the first method also we
  // use the same.
  @PostMapping("/createStudentInDBForTestRestControllerAdvice")
  public ResponseEntity<?> insertStudentData(@RequestBody @Valid Student student) {
    LOG.info("Hit insertStudentData API");
    Boolean result = studentService.saveStudentDataInDB(student);
    if (result) {
      return new ResponseEntity<>("Done dana done done", HttpStatus.CREATED);
    }
    return new ResponseEntity<>("Not Done", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // In below API we will try to fetch the data of a student and if the student not found then we
  // will throw a
  // custom exception which will be handled in the @RestControllerAdvice and we will send a custom
  // response along with
  // the HTTP Status.
  @GetMapping("/customExceptionWithRestControllerAdvice/{rollNo}")
  public ResponseEntity<?> getStudentByRollNoWithRestControllerAdvice(
      @PathVariable("rollNo") int roll) {
    LOG.info("Hit getStudentByRollNoWithRestControllerAdvice API");
    Student studentData = studentService.getStudentByRollUsingRestControllerAdvice(roll);
    LOG.info("Found the data for the id {}", roll);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }

  // Below method will add only student from college defined in environment variables
  @PostMapping("/createStudentInDBForACollegeOnly")
  public ResponseEntity<?> insertStudentDataForACollegeOnly(@RequestBody @Valid Student student) {
    LOG.info("Hit insertStudentDataForACollegeOnly API");
    Boolean result = studentService.saveStudentDataForACollegeOnly(student);
    return new ResponseEntity<>("Done dana done done", HttpStatus.CREATED);
  }
}
