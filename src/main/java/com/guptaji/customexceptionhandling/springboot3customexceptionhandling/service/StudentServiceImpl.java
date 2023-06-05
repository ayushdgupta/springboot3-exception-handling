package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.ResourceNotFoundException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFoundException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.repository.StudentRepo;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  Logger LOG = LogManager.getLogger(StudentServiceImpl.class);

  private static final int offset = 10;

  @Autowired private StudentRepo studentRepo;

  @Override
  public Student getStudentByRollDefaultExceptionHandling(int roll) {
    LOG.info("getStudentByRollDefaultExceptionHandling API Hit");
    return studentRepo
        .findById(roll)
        .orElseThrow(
            () -> {
              LOG.error("No Student found for the roll no {}", roll);
              return new NoSuchElementException("No Data found for the roll no. " + roll);
            });
  }

  @Override
  public Student addNewStudentData(Student student) {
    LOG.info("HIT addNewStudentData Service");
    return studentRepo.save(student);
  }

  @Override
  public Student getStudentByRollUsingCustomExceptionHandling(int roll) {
    LOG.info("getStudentByRollDefaultExceptionHandling API Hit");
    return studentRepo
        .findById(roll)
        .orElseThrow(
            () -> {
              LOG.error("No Student found for the roll no {}", roll);
              return new ResourceNotFoundException("Student", "RollNo", Integer.toString(roll));
            });
  }

  @Override
  public Boolean saveStudentDataInDB(Student student) {
    LOG.info("saveStudentDataInDB Service Hit");
    Student savedStudentData = studentRepo.save(student);
    // Here I am deliberately throwing the exception by adding one offset to test
    // @RestControllerAdvice
    studentRepo
        .findById(student.getRollNo() + offset)
        .orElseThrow(
            () -> {
              LOG.error("No Student found for the roll no {}", student.getRollNo() + offset);
              return new ResourceNotFoundException(
                  "Student", "RollNo", Integer.toString(student.getRollNo() + offset));
            });
    return true;
  }

  @Override
  public Student getStudentByRollUsingRestControllerAdvice(int roll) {
    return studentRepo
        .findById(roll)
        .orElseThrow(
            () -> {
              LOG.error("No Student found with the roll no " + roll);
              return new StudentNotFoundException(
                  "StudentService", "RollNo", Integer.toString(roll));
            });
  }
}
