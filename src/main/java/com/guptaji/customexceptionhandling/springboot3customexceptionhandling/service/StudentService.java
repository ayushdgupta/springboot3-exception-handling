package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;

public interface StudentService {

  Student getStudentByRollDefaultExceptionHandling(int roll);

  Student addNewStudentData(Student student);

  Student getStudentByRollUsingCustomExceptionHandling(int roll);

  Boolean saveStudentDataInDB(Student student);

  Student getStudentByRollUsingRestControllerAdvice(int roll);

  Boolean saveStudentDataForACollegeOnly(Student student);
}
