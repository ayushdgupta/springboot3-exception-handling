package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

// All the validations like @NotNull, @NotBlank etc. are on our entity level only there will be no
// change on DB
// level.
@Entity
@Table(name = "studentExceptionHandling")
public class Student {

  @Id public int rollNo;

  @NotNull(message = "Baap ne jo naam diya hai vo bhej bhootni ke")
  public String name;

  @Email(message = "Sahi email dal bhootni ke")
  public String email;

  @NotNull
  @Pattern(regexp = "(^$|[0-9]{10})")
  public String phoneNo;

  @NotBlank(
      message =
          "Mera aadhar meri pehchaan if you don't have aadhar then you are rohingya bhaag yha se")
  public String aadharNo;

  public String collegeName;

  public Student() {
    // default constructor
  }

  public Student(
      int rollNo, String name, String email, String phoneNo, String aadharNo, String collegeName) {
    this.rollNo = rollNo;
    this.name = name;
    this.email = email;
    this.phoneNo = phoneNo;
    this.aadharNo = aadharNo;
    this.collegeName = collegeName;
  }

  public int getRollNo() {
    return rollNo;
  }

  public void setRollNo(int rollNo) {
    this.rollNo = rollNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getAadharNo() {
    return aadharNo;
  }

  public void setAadharNo(String aadharNo) {
    this.aadharNo = aadharNo;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName;
  }

  @Override
  public String toString() {
    return "Student{"
        + "rollNo="
        + rollNo
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", phoneNo='"
        + phoneNo
        + '\''
        + ", aadharNo='"
        + aadharNo
        + '\''
        + ", collegeName='"
        + collegeName
        + '\''
        + '}';
  }
}
