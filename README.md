Exception Handling in Spring-Boot can be done in three ways -

### 1. Default Exception Handling of Spring Boot

- Here whenever exception will be thrown from the controller then a default message in json will be shown to user / caller / client.

```
{
  "timestamp": "2023-05-29T04:13:09.928+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "trace": "java.util.NoSuchElementException: No Data found for the roll no. 1\r\n\tat com.guptaji.customexceptionhandling.springboot3customexceptionhandling.service.StudentServiceImpl.lambda$getStudentByRoll$0(StudentServiceImpl.java:28)\r\n\tat java. ----- ENTIRE TRACE ------",
  "message": "No Data found for the roll no. 1",
  "path": "/student/1"
}
```
- In our controller below API is implementing default spring-boot exception handling approach -  
`getStudentByRollNo(@PathVariable("rollNo") int roll)`

- In above response we only provide the message in the exception neither the status nor anything that status and all spring boot is showing on it's own.  

- So the above response can be customized using few properties in application.yaml / properties - [Click on this link to know more on those properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.server) while few of them are -  

```
server:
    error:                                          
        include-message: ALWAYS
        include-binding-errors: ALWAYS
        include-stacktrace: ON_PARAM
        include-exception: false
```  

- With the above properties also we can customize our error response upto a certain extent but still that method also has
some limits e.g. we can change the message, but we can't change the error header and we can n't change the response status,
everytime the status is going as 500 that we can't change.