# rest-prime-number-generator
RESTful service that calculates prime numbers. The service generates prime number with upper range upto 2^63-1. Long.MAX_VALUE

The service has been developed using Java 8 and Spring Boot Web and runs on port 8080. You can use swagger to trigger the prime number generation - http://localhost:8080/swagger-ui.html

The service will asynchronously generate prime numbers. On submission of request, it will return a resultid which can be used to get the results from the service.
The service caches the results for 3 hours. For tasks running longer than 3 hours, this cache time must be set appropriately.

It implements various different methods to generate prime numbers which can be looked up using http://localhost:8080/api/v1/primes/algorithmNames
  "FORK_JOIN",
  "ITERATIVE",
  "PARALLEL_STREAM",
  "STREAM",
  "ERATOSTHENES_SIEVE"
  
The build tool is maven.
The unit tests cases has been written using TestNG and Mockito. TestNG is to test the algorithms/methods of prime generation. Mockito has been used to test the primes service

