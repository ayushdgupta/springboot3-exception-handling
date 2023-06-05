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

### 2. Using Custom exceptions with @ResponseStatus

- Here we create one custom exception and extend it with RuntimeException and then use __@ResposnseStatus()__ on the exception class.
- @ResponseStatus, in combination with the server.error configuration properties, allows us to manipulate almost all the fields in our Spring-defined error response payload.
- But here also we can't change the structure of the response (Response header).
- Below API uses this method -   
`getStudentByRollNoWithCustomException(@PathVariable("rollNo") int roll)`

### 3. Using @RestControllerAdvice and @ExceptionHandler

- In Both above technique we were trying to manipulate the error response header but we were not able to change the response header of it.
- But with the help of @RestControllerAdvice we can change the response header as well.
- And all the exception can be handled with in the same class.
- @RestControllerAdvice = @ControllerAdvice + @ResponseBody.
- With @ControllerAdvice we can handle the exceptions globally but the response header will not go into our required format so if we want to
change the response header as well then we need to use @ResponseBody.
- @ResponseBody indicates that our methods’ return values should be bound to the web response body. To put it simply- our handler methods returning some ExampleClass will be treated as ResponseEntity<ExampleClass> out of the box. As I have said, the @RestControllerAdvice combines it with the @ControllerAdvice, so that we do not have to annotate our methods explicitly.
- In our Controller below two API Uses @RestControllerAdvice
```
1. public ResponseEntity<?> insertNewStudent(@RequestBody @Valid Student student)
2. public ResponseEntity<?> getStudentByRollNoWithRestControllerAdvice
```

## Follow below links for exception handling 
1. https://reflectoring.io/spring-boot-exception-handling/
2. https://codersee.com/exception-handling-with-restcontrolleradvice-and-exceptionhandler/