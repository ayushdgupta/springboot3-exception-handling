package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  private String resourceName;
  private String fieldName;
  private Object fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(
        String.format(
            "%s not found for the field %s with value %s", resourceName, fieldName, fieldValue));
    // setting up these fields has no usage, so if we want we can comment them as well.
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }

  public void setFieldValue(Object fieldValue) {
    this.fieldValue = fieldValue;
  }
}
