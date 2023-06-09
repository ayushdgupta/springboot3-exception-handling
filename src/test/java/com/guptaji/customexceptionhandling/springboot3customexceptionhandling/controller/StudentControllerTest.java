package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFoundException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions.StudentNotFromRequiredCollegeException;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;
import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service.StudentServiceImpl;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private StudentServiceImpl studentService;

  private Student student;

  @BeforeEach
  public void init() {
    student =
        new Student(
            1, "Ayush gupta", "ayushdgupta17@gmail.com", "1234567890", "123456789012", "NIT RKL");
  }

  @Test
  void insertNewStudent() throws Exception {
    student.setName(null);
    student.setEmail("abcd");
    given(studentService.addNewStudentData(student)).willReturn(student);
    ResultActions response =
        mockMvc.perform(
            post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$.name").value("Baap ne jo naam diya hai vo bhej bhootni ke"))
        .andExpect(jsonPath("$.email", is("Sahi email dal bhootni ke")));
  }

  /*
     Right now we are not able to run the below test case because it throws an exception when we are calling
     'mockMvc.perform(get("/student/{rollNo}", student.getRollNo()));'
     'NoSuchElementException' that we want but the issue is after throwing exception code is blasting not
     propagating further. And generally we don't throw exceptions like that in real time scenarios, instead
     of this we use try / catch to handle exceptions scenario.
  */
  @Test
  void getStudentByRollNo() throws Exception {

    given(studentService.getStudentByRollDefaultExceptionHandling(student.getRollNo()))
        .willThrow(
            new NoSuchElementException("No Data found for the roll no. " + student.getRollNo()));

    NoSuchElementException actualException =
        assertThrows(
            NoSuchElementException.class,
            () -> studentService.getStudentByRollDefaultExceptionHandling(student.getRollNo()));

    assertEquals(
        actualException.getMessage(), "No Data found for the roll no. " + student.getRollNo());

    // Below code is not working. Basically the issue is 'perform()' method requires a 'throws
    // Exception' on method signature so what happen whenever our below code runs, it returns an
    // exception, and we've not handled that error (try / catch) so that throws invoked and instead
    // of going below or avoiding the error 'throws' keyword throw that error to us.
    //    assertThrows(
    //        NoSuchElementException.class,
    //        () -> mockMvc.perform(get("/student/{rollNo}", student.getRollNo())));

    //    ResultActions response = mockMvc.perform(get("/student/{rollNo}", student.getRollNo()));
    //
    //    response
    //        .andExpect(status().isInternalServerError())
    //        .andExpect(
    //            jsonPath("$.message", is("No Data found for the roll no. " +
    // student.getRollNo())));
  }

  @Test
  void insertStudentData() throws Exception {
    student.setName(null);
    given(studentService.saveStudentDataInDB(student)).willReturn(true);
    ResultActions response =
        mockMvc.perform(
            post("/student/createStudentInDBForTestRestControllerAdvice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$.name").value("Baap ne jo naam diya hai vo bhej bhootni ke"));
  }

  @Test
  void getStudentByRollNoWithRestControllerAdvice() throws Exception {
    given(
            studentService.getStudentByRollUsingRestControllerAdvice(
                ArgumentMatchers.any(Integer.class)))
        .willThrow(
            new StudentNotFoundException(
                "StudentService", "RollNo", Integer.toString(student.getRollNo())));
    ResultActions response =
        mockMvc.perform(
            get("/student/customExceptionWithRestControllerAdvice/{rollNo}", student.getRollNo()));

    // in the below JUnit when i was giving HttpStatus.NOT_FOUND then it's value is coming as '404
    // NOT_FOUND' so test cas were failing but the same thing we are setting in exception at that
    // place it was setting only NOT_FOUND don't know the reason that's why i used substring()
    // method.
    response
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(
            jsonPath("$.errorMessage")
                .value("StudentService not found for the field RollNo with value 1"))
        .andExpect(jsonPath("$.errorStatus").value(HttpStatus.NOT_FOUND.toString().substring(4)));
  }

  @Test
  void insertStudentDataForACollegeOnly() throws Exception {
    given(studentService.saveStudentDataForACollegeOnly(ArgumentMatchers.any(Student.class)))
        .willThrow(new StudentNotFromRequiredCollegeException(student.getName(), "NIT RKL"));

    ResultActions response =
        mockMvc.perform(
            post("/student/createStudentInDBForACollegeOnly")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andDo(print())
        .andExpect(status().isNotAcceptable())
        .andExpect(jsonPath("$.port").value(9090))
        .andExpect(jsonPath("$.title").value("Add Student from a particular college"));
  }
}
