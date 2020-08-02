![](logo.png)
# Checking Credit Cards

### Synopsis

This is my solution to the Credit Cards validation coding challenge. The purpose of this service is to act as a REST API that 
validates different kinds of credit cards.

### Technical Details
This program was written as a Java 1.8 Spring Boot microservice which provides a single Restful endpoint, for performing the credit card validations.

The service returns a textual message indicating if the card is valid or invalid. It also returns an HTTP response status, as follows:

| Http Status   |   Description    |  Test Case |
|:-------------:|:-----------------|------------|
| 200 | Credit Card is valid  | curl -I http://localhost/api/1.0/card/validate/378282246310005  |
| 400 | Credit Card is invalid   | curl -I http://localhost/api/1.0/card/validate/378282246310999  |
| 422 |Credit Card does not conform to the set of supported types | curl -I http://localhost/api/1.0/card/validate/9111111111111111  |
    
#### Test Cases

During the design and coding of this application, particular attention has been paid to TDD, resulting in the creation of **32** Unit Test cases. 
The Intellij code coverage was run, revealing that **100%** of classes and **95%** of lines were covered by the Unit/Integration Tests.

###  Swagger Documentation
 Ensure that the application is running on your local machine. You may then click on the following link to see the API documentation:
 
 > [Credit Card Validation Service API](http://localhost/swagger-ui.html)

 
 ### How to run the application
 You machine must have the following installed on your machine to run this application:
 - Java 1.8 compiler 
 - Apache Maven 3 
 
 Then perform the following:
 ```
 1. mvn clean install
 2. mvn spring-boot:run
 3. Then connect with your browser, postman or curl, as documented in the above test cases
 ```

### Issues & Assumptions

In this section I will highlight a couple of issues that I was considering during the development of this service. 

I was mindful of keeping my solution as simple as possible, as per the requirements, without going overboard ([Yagni](https://en.wikipedia.org/wiki/You_aren%27t_gonna_need_it)).

In a real world situation, I am sure that there would be a fair amount of discussion and consideration, as new requirements are brought to light..

#### Improvements

- The business logic for determining the type of credit card being used, could benefit from being composed in a separate service (and indeed a separate library). This service might also use a set of spring configuration elements, rather than relying upon hard coding.
- There is nothing in the requirements, but it would be nice to return a JSON object containing the card type and valid/invalid message (or a boolean).
- No mention of security concerns, but it would be very easy to add basic authentication. This 'easy' solution, uncovers a lot of questions about who might manage these credentials, or indeed monitor their use, in production.
- The Unit Tests are fine as they are, but if [Junit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5) where used, the code would be cleaner. I am not a big fan of the Java version of this, but I *do* like the [Groovy Spockito library](https://github.com/tools4j/spockito). Unfortunately, this library has not been updated to support JUnit 5 🤷. 

#### Performance

Considering that this service might be part of a scalable, high availability, fault tolerant infrastructure, the speed and performance are paramount.

- Productionising this service into AWS, an EC2 with ASG across multi-AZ, grouped together with an ELB would likely be used. This would be acceptable performance wise, as it would rely upon horizontal scaling.
- Alternatively, it would be super easy to switch to using AWS Lambda for the serverless approach (FaaS). This would result in a cost reduction and simplify the service management significantly.
- Each of these improvements would benefit from using an AWS ElastiCache. As the validity of a particular credit card number never changes, the TTL associated with the cache could be very long indeed.
- Finally, there is a different and rather novel solution to this problem, which admittedly could be classified as a 'Boil the Ocean' approach. It would rely upon an EC2 service, which would require a *lot* of memory. 
Upon startup during intialisation, the service would iterate through *each* and *every* credit card number and determine using the Lunah algorithm, if it is valid or not. This information would be stored in a boolean array, with the key being the credit card number. 
This means that the validity check would be instant, as it would simply require a lookup from the array.
This would mean super fast validations, with the disadvantage of this approach being the time taken during intialisation.      

### About the Developer

**Colin Schofield**   
e: colin_sch@yahoo.com  
p: 0448 644 233  
l: https://www.linkedin.com/in/colins/
