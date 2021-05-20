## Timezone API

### Functionality
A REST API that shows time in different timezones

* API Users must be able to create an account and log in.
* All API calls must be authenticated.
* When logged in, a user can see, edit and delete timezones he entered.
* Implement 2 roles with different permission levels: a regular user would only be able to CRUD on their owned records, 
  and an admin would be able to CRUD all users and all user records.
* When a timezone is entered, each entry has a Name, Name of the city in timezone, the difference to GMT time.
* The API must be able to return data in the JSON format.

### Design & Implementation

The API has been designed using <a link="https://docs.oracle.com/en/java/javase/11/docs/api/index.html">Java 11</a> 
and <a link="https://spring.io/projects/spring-boot">Spring Boot</a>. 
<a link="https://redislabs.com/">Redis</a> is used for storage of timezones. It can either be used in embedded manner in standalone mode or as a separate 
entity when running using docker. The backend storage is accessed via a set of interfaces. It can easily be swapped 
out for anything else without affecting rest of the application.

### Testing
There are two categories of tests available to execute.
* Unit tests: These can be triggered using the following command.  
```
./gradlew clean check
```
  
* Functional tests: The functional tests verify correctness of the application as a black . Hence, they need the 
  application to be running before they can be triggered.
  - To start the application, run the following command.
    ```
    ./gradlew clean build bootRun 
    ```
  - Once the application is running, the functional tests can be triggered using the following command.
    ```
    ./gradlew remoteFunctional
    ```

A code coverage plugin <a link="https://www.jacoco.org/jacoco/">Jacoco</a> has been added to the project in order to 
keep the test coverage in check. This plugin would automatically run whenever the unit tests are executed. The plugin
generates an HTML report that can be found at the following path.
```
application/build/jacocoHtml/index.html
```

In order to run <a link="https://www.jacoco.org/jacoco/">Jacoco</a> separately, it can be triggered using the following 
command.
```
./gradlew application:jacocoTestReport
```

### Postman Collection
A postman collection has been added to the project if you need a more user-friendly way to explore the APIs of the 
application. It can be found at the root directory of the project by the name of `TimezoneAPI.postman_collection.json`

### Starting the application
The application can either be started in Standalone mode where everything runs in-memory or in the non-standalone mode 
where it could run inside a docker container.

* To start the application in standalone mode, use the following command. By default, this would start the application 
on port 8080. Refer the [Configuration](#configuration) section, if you would like to switch to a different port.
```
./gradlew clean build bootRun 
```
* To start the application in non-standalone mode, a <a link="https://docs.docker.com/compose/">docker-compose</a> file 
has been provided that contains configuration for two containers. The first container is for the application, while the 
  other is for database (<a link="https://redislabs.com/">Redis</a>). Docker compose can be started using the following
  command, however as a prerequisite it will require installation of Docker and Docker Compose on the host. Refer the 
  website of <a link="https://docs.docker.com/">docker</a> for details.
```
docker-compose up -d --build
```

### Stopping the application
In standalone mode, the application can be stopped by pressing `Ctrl-C`. This would send a `SIGTERM` to the application
that would terminate it gracefully. For application, running in docker container, use `docker stop <container_name>` or
spin down the docker compose stack.

### Observability
Once the application is running, it's important to be able to monitor the operations of the application. The following 
options are available monitor the application state.
#### `status` endpoint
  This endpoint would help to determine if the application is available to serve its users. If the application is able to
  serve its users, it would return an HTTP status code of 200 with a text of "OK" as the response.

#### Logging
  The application employs <a link="http://www.slf4j.org/">SLF4J</a> api for logging. The properties for that can be found
  under `application/src/main/resources`. The default logging level has been set to `INFO`. 

#### Request-ID
  For every request, the application assigns a unique request-ID and attaches it the request and response header. The 
  request-ID appears alongside each log statement of the transaction and is also returned as part of the response. This
  should simplify any kind of request tracing that me be needed to perform.

#### Call Duration
  For every request, the application also logs the total time it took for application to perform the operation. This time
  is also recorded into a histogram for easier analysis.

#### `metrics` endpoint
  JVM metrics can be accessed using an endpoint provided in the application. This would provide JVM metrics and all the 
  other functional metrics provided in the application. The following metrics are available 
  - `request_latency` is a histogram to analyse endpoints latency.
  - `request_count` is a counter to record request counts for a given HTTP method and path.
  - `exceptionCount` ia a counter to provide the number of exceptions occurred by HTTP status code.

### Execution Environment
The application runs on <a link="https://docs.oracle.com/en/java/javase/11/docs/api/index.html">Java 11</a> which means 
that for application in standalone mode, it would need a Java installation. The application does not do that by itself.

For non-standalone mode, it just needs docker host and docker compose installations mentioned above.

### Configuration
