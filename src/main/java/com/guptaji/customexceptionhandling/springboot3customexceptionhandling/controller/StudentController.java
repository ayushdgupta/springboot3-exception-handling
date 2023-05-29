package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.controller;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service.StudentServiceImpl;

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

  @PostMapping
  public ResponseEntity<?> insertNewStudent(@RequestBody Student student) {
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
}
