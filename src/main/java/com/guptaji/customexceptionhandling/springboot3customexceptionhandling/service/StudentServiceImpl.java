package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service;

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

  @Autowired private StudentRepo studentRepo;

  @Override
  public Student getStudentByRoll(int roll) {
    LOG.info("getStudentByRoll API Hit");
    return studentRepo
        .findById(roll)
        .orElseThrow(
            () -> {
              LOG.error("No Student found for the roll no {}", roll);
              return new NoSuchElementException("No Data found for the roll no. " + roll);
            });
  }
}
