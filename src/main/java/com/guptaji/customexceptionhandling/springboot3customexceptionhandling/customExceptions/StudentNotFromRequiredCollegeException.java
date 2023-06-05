package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions;

public class StudentNotFromRequiredCollegeException extends RuntimeException {
  private String StudentName;
  private String collegeName;

  public StudentNotFromRequiredCollegeException(String StudentName, String collegeName) {
    super(String.format("Student %s not belongs to the college %s", StudentName, collegeName));
    this.StudentName = StudentName;
    this.collegeName = collegeName;
  }

  public String getStudentName() {
    return StudentName;
  }

  public void setStudentName(String studentName) {
    StudentName = studentName;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName;
  }
}
