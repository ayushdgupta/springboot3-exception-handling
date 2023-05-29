package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.controller;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service.StudentServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

  Logger LOG = LogManager.getLogger(StudentController.class);

  @Autowired private StudentServiceImpl studentService;

  // M1-> Default Spring Boot exception handling
  @GetMapping("/{rollNo}")
  public ResponseEntity<?> getStudentByRollNo(@PathVariable("rollNo") int roll) {
    LOG.info("Hit getStudentByRollNo API");
    Student studentData = studentService.getStudentByRoll(roll);
    LOG.info("Found the data for the id {}", roll);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }
}
