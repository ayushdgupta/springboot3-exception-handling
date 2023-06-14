package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.ResourceNotFoundException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFoundException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFromRequiredCollegeException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.repository.StudentRepo;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
class StudentServiceImplTest {

  @Mock private StudentRepo studentRepo;

  @InjectMocks private StudentServiceImpl studentService;

  private Student student;

  @BeforeEach
  public void init() {
    student =
        new Student(
            1, "Ayush gupta", "ayushdgupta17@gmail.com", "1234567890", "123456789012", "NIT RKL");
  }

  @Test
  void getStudentByRollDefaultExceptionHandling() {
    given(studentRepo.findById(ArgumentMatchers.any(Integer.class)))
        .willThrow(
            new NoSuchElementException("No Data found for the roll no. " + student.getRollNo()));

    NoSuchElementException exception =
        assertThrows(
            NoSuchElementException.class,
            () -> studentService.getStudentByRollDefaultExceptionHandling(student.getRollNo()));

    assertEquals(exception.getMessage(), "No Data found for the roll no. " + student.getRollNo());
  }

  @Test
  void getStudentByRollUsingCustomExceptionHandling() {
    given(studentRepo.findById(ArgumentMatchers.any(Integer.class)))
        .willThrow(
            new ResourceNotFoundException(
                "Student", "RollNo", Integer.toString(student.getRollNo())));

    ResourceNotFoundException exception =
        assertThrows(
            ResourceNotFoundException.class,
            () -> studentService.getStudentByRollUsingCustomExceptionHandling(student.getRollNo()));

    assertEquals(
        exception.getMessage(),
        "Student not found for the field RollNo with value " + student.getRollNo());
  }

  @Test
  void getStudentByRollUsingRestControllerAdvice() {
    given(studentRepo.findById(ArgumentMatchers.any(Integer.class)))
        .willThrow(
            new StudentNotFoundException(
                "StudentService", "RollNo", Integer.toString(student.getRollNo())));

    StudentNotFoundException exception =
        assertThrows(
            StudentNotFoundException.class,
            () -> studentService.getStudentByRollUsingRestControllerAdvice(student.getRollNo()));

    assertEquals(
        exception.getMessage(),
        "StudentService not found for the field RollNo with value " + student.getRollNo());

    assertEquals(exception.getResourceName(), "StudentService");
  }

  @Test
  void saveStudentDataForACollegeOnly() {
    /*
       When I was testing this test case then our test case was failing because it was not able to
       read values from application.properties during JUnit in our service class so for that we use
       'ReflectionTestUtils.setField()' now it is working absolutely fine.
    */
    ReflectionTestUtils.setField(studentService, "college", "IIT Kanpur");
    student.setCollegeName("IIT Roorkee");

    StudentNotFromRequiredCollegeException exception =
        assertThrows(
            StudentNotFromRequiredCollegeException.class,
            () -> studentService.saveStudentDataForACollegeOnly(student));

    assertEquals(
        exception.getMessage(),
        String.format("Student %s not belongs to the college %s", student.getName(), "IIT Kanpur"));
  }
}
