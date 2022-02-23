[![Java CI with Gradle](https://github.com/Tyagi-Gaurav/Movie/actions/workflows/functionalTest.yml/badge.svg)](https://github.com/Tyagi-Gaurav/Movie/actions/workflows/functionalTest.yml)
[![Sonar](https://github.com/Tyagi-Gaurav/Movie/actions/workflows/sonar.yml/badge.svg)](https://github.com/Tyagi-Gaurav/Movie/actions/workflows/sonar.yml)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Tyagi-Gaurav_Movie&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Tyagi-Gaurav_Movie)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Tyagi-Gaurav_Movie&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Tyagi-Gaurav_Movie)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Tyagi-Gaurav_Movie&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Tyagi-Gaurav_Movie)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Tyagi-Gaurav_Movie&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Tyagi-Gaurav_Movie)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Tyagi-Gaurav_Movie&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Tyagi-Gaurav_Movie)

## Movie API

### Engineering Principles

<table>
    <thead>
        <tr>
            <th></th>
            <th>Content-Upload</th>
            <th>User</th>
            <th>Issue#</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Clearly documented interfaces</td>
            <td>(x)</td>
            <td>(x)</td>
            <td>#212</td>
        </tr>
        <tr rowspan="2">
            <td>
                    Resilience libraries configured for any downstream<br>
                    <li>Circuit breaker correctly configured for downstreams</li>
                    <li>Timeouts for downstreams</li>
                    <li>Bulkhead per endpoint</li>
            </td>
            <td>(x)</td>
            <td>(x)</td>
            <td>#194</td>
        </tr>
        <tr>
            <td>Intra service communication via GRPC</td>
            <td>(x)</td>
            <td>NA</td>
            <td></td>
        </tr>
    </tbody>
</table>

### Functionality

A REST API that allows users to update movie ratings

* API Users must be able to create an account and log in.
* All API calls must be authenticated.
* When logged in, a user can see, edit and delete movie ratings that they entered.
* There are two roles with different permission levels:
    * A regular user that can only access their own records.
    * An admin that can CRUD all users and all user records.
* When a movie is entered, each entry has a Name, Rating of the movie, year of production.
* The API must be able to return data in the JSON format.

### Design & Implementation

The API has been designed using [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
and [Spring Boot](https://spring.io/projects/spring-boot). [MySQL](https://www.mysql.com/) is used for storage of
movies. It can either be used in embedded manner in standalone mode or as a separate entity when running using docker.
The backend storage is accessed via a set of interfaces. It can easily be swapped out for anything else without
affecting rest of the application.

### Java Version

The application current runs on Java 16.

#### Application Security

To access the application, users need a valid `userId` and `password` to login. Once they login, they would be provided
a security token ([JWT](https://jwt.io/))

### User Registration with Google, Github credentials
The application also supports login to App via Google and Github credentials. When the `ui` container
is running locally, the page can be accessed via 
```
http://localhost:8080/ui/index.html
```

### Testing

There are three categories of tests available to execute.

* Unit tests: These are a set of fast running tests that usually finish within a minute. They  can be triggered using 
  the following command.

```
./gradlew clean check
```

* Integration tests: These are a set of slow running tests that spin up the entire application in memory before running
the tests. They can be triggered separately using.
```
./gradlew clean integrationTest
```

* GRPC Integration tests: These are a set of slow running tests that spin up the entire application in memory before running
  the GRPC tests. They can be triggered separately using.
```
./gradlew clean grpcIntegrationTest
```

* Functional tests: The functional tests verify correctness of the application as a black box. Hence, they need the
  application to be running before they can be triggered.
    - To start the application, run the following command.
      ```
      ./gradlew clean build bootRun
      ```
      or spin up the docker compose stack
      ```
      docker-compose up -d --build
      ```

    - Once the application is running, the functional tests can be triggered using the following command.
      ```
      ./gradlew remoteFunctional
      ```

A code coverage plugin [Jacoco](https://www.jacoco.org/jacoco/") has been added to the project in order to keep the test
coverage in check. This plugin would automatically run whenever the unit tests are executed. The plugin generates an
HTML report that can be found at the following path.

```
application/build/jacocoHtml/index.html
```

In order to run [Jacoco](https://www.jacoco.org/jacoco/") separately, it can be triggered using the following command.

```
./gradlew application:jacocoTestReport
```

### Postman Collection

A postman collection has been added to the project if you need a more user-friendly way to explore the APIs of the
application. It can be found at the root directory of the project by the name of `MovieAPI.postman_collection.json`

### Starting the application

The application can either be started in Standalone mode where everything runs in-memory or in the non-standalone mode
where it could run inside a docker container.

* To start the application in standalone mode, use the following command. By default, this would start the application
  on port 8080. Refer the [Configuration](#configuration) section, if you would like to switch to a different port.

```
./gradlew clean build bootRun 
```

* To start the application in non-standalone mode, a [Docker Compose](https://docs.docker.com/compose/") file has been
  provided that contains configuration for two containers. The first container is for the application, while the other
  is for database ([Redis](https://redislabs.com/)). Docker compose can be started using the following command, however
  as a prerequisite it will require installation of Docker and Docker Compose on the host. Refer the website
  of [docker](https://docs.docker.com) for details.

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

The application employs [SLF4J](http://www.slf4j.org/) api for logging. The properties for that can be found
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

The metrics endpoint can be accessed via the management port of the application. When running the app locally the URL
would be `http://localhost:8081/actuator/metrics`. This would return a list of metric names.

To get details on an individual metric, we could use `http://localhost:8081/actuator/metrics/<metricName>`.

### Execution Environment

The application runs on [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html) which means that for
application in standalone mode, it would need a Java installation. The application does not do that by itself.

For non-standalone mode, it just needs docker host and docker compose installations mentioned above.

### Configuration

| Config Variable     | Default Value     | Description | 
| :-------------      | :----------:      | :----------:|
|  SERVER_PORT        | 8080        | Default HTTP port for the application. |
| SERVER_MGT_PORT     | 8081      | Default management port for the application. |
| TOKEN_DURATION     | 1 hour      | The default duration of a valid JWT token. Upon expiry, the user will have to re-login. | 
| SECRET     |       19CA249C582715657BDCAB1FB31E69F854443A4FE3CBAFFD215E3F3676 | The default secret for creating security tokens | 
| MYSQL_HOST     | localhost      | The host for database |
| MYSQL_HOST     | 3306      | The port for database |
| MYSQL_USER     | user      | User for accessing database |
| MYSQL_PWD     | password      | Password to access database |
| MYSQL_DB_NAME     | 3306      | Database name to connect to  |
| DUPLICATE_INTERVAL     | 10s      | The default interval within which if a user sends a duplicate request, that would be rejected. |
| CONNECTION_TIMEOUT     | 10s      | Connection timeout for the database. |
| COMMAND_TIMEOUT     | 500ms      | The default query timeout for all database queries. |
| GracefulShutdownWaitSeconds     | 20 seconds      | The time application waits to terminate after receiving a SIGTERM signal. |

### REST Endpoints

#### Create User (REST)

Use this to create a new regular user or admin user account.

| Method     | URL     | 
| :-------------      | :----------:      |
| POST | /api/user/account/create |

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| userName | String | User name that will be used for login. Should be between 4 & 20 characters. |
| password | String | Password that will be used for login. Can be alphanumeric between 6 & 15 characters. |
| firstName | String | Users first name. Should be between 3 & 30 characters. | 
| lastName | String | Users last name. Should be between 3 & 30 characters. |
| role | String | Can be either `USER` or `ADMIN` |

| Headers | Value |
| :-------------      | :----------:      | 
| Content-Type | `application/vnd+account.create.v1+json` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful account creation |

**Sample Request**

```
{
    "userName" : "admin",
    "password" : "admin123",
    "firstName" : "Test First Name",
    "lastName" : "Test Last Name",
    "role" : "ADMIN"
}
```

#### Login (REST)

Used to gain access to the application

| Method     | URL     | 
| :-------------      | :----------:      |
| POST | /api/user/login |

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| userName | String | User name that will be used for login. Should be between 4 & 20 characters. |
| password | String | Password that will be used for login. Can be alphanumeric between 6 & 15 characters. |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.login.v1+json` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful login |
| Body Attribute `token` | String | Token to be used for subsequent API calls |
| Body Attribute `id` | String | User's ID |

**Sample Request**

```
{
    "userName" : "admin", 
    "password" : "user123"
}
```

**Sample Response**

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJBdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiVVNFUiJ9XSwic3ViIjoidXNlcjEiLCJqdGkiOiI0NDU3NzkzYy0yYmM4LTQxNTItYWY0ZC1iNTljMGUwNTk3OGUiLCJpYXQiOjE2MjE0NTkxOTcsImV4cCI6MTYyMTQ4MDc5N30.PFFKY9H1oaFngeMazwSvPMcjCOx4oUEqezRg0xyrvgk",
    "id": "4457793c-2bc8-4152-af4d-b59c0e05978e"
}
```

#### Add Movie (REST)

To add new movie(s).

| Method     | URL     | 
| :-------------      | :----------:      | 
| POST | /api/user/movie | 

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| name | String | Movie Name |
| rating | Double | Moving rating between 1 and 10 |
| yearProduced | integer |  |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.add.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful movie creation |
| HTTP Status code | 401 | If the user is not authenticated |

**Sample Request**

```
{
    "name" : "Second Blood",
    "rating" : 7.19,
    "yearProduced" : 2005
}
```

#### Admin Add Movie (REST)

This endpoint can be used by admin to create movies for a specific user.

| Method     | URL     | 
| :-------------      | :----------:      | 
| POST | /api/user/movie |

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the movie must be created |

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| name | String | Movie Name |
| gmtOffset | integer | Offset to GMT. Valid values must be between `-12` to `+12` (Both Inclusive) |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.add.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful movie creation |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "name" : "Second Blood",
    "rating" : 7.19,
    "yearProduced" : 2005
}
```

#### Read Movie (REST)

To read movies created by the user

| Method     | URL     | 
| :-------------      | :----------:      |
| GET | /api/user/movie | 

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.read.v1+json` | 
| Accept | `application/vnd.movie.read.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful read of movie       |
| HTTP Status code | 401 | If the user is not authenticated  |

**Sample Response**

```
{
    "movies": [
        {
            "id": "5a39e12c-b8f2-40b2-8a0a-1ad8fac13656",
            "name": "Third Blood",
            "yearProduced": 2005,
            "rating": 7.19
        }
    ]
}
```

#### Admin Read Movie (REST)

To read movies created by the user

| Method     | URL     | 
| :-------------      | :----------:      |
| GET | /api/user/movie | 

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the movie must be created |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.read.v1+json` | 
| Accept | `application/vnd.movie.read.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful read of movie |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Response**

```
{
    "movies": [
        {
            "id": "5a39e12c-b8f2-40b2-8a0a-1ad8fac13656",
            "name": "Third Blood",
            "yearProduced": 2005,
            "rating": 7.19
        }
    ]
}
```

#### Update Movie (REST)

To update movies for the user.

| Method     | URL     | 
| :-------------      | :----------:      |
| PUT | /api/user/movie |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.update.v1+json` | 
| Accept | `application/vnd.movie.update.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful update of movie |
| HTTP Status code | 401 | If the user is not authenticated |

**Sample Request**

```
{
    "id": "5a39e12c-b8f2-40b2-8a0a-1ad8fac13656",
    "name": "Third Blood"
}
```

Any of the movie fields could be changed, except for the `id` field as it uniquely identifies the movie.

#### Admin Update Movie (REST)

To update movies for the user or for a specific user.

| Method     | URL     | 
| :-------------      | :----------:      |
| PUT | /api/user/movie |

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the movie must be updated |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.update.v1+json` | 
| Accept | `application/vnd.movie.update.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful update of movie |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "id": "5a39e12c-b8f2-40b2-8a0a-1ad8fac13656",
    "name": "Third Blood"
}
```

Any of the movie fields could be changed, except for the `id` field as it uniquely identifies the movie.

#### Delete Movie (REST)

To delete movies for the user

| Method     | URL     | 
| :-------------      | :----------:      | 
| DELETE | /api/user/movie | 

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.delete.v1+json` | 
| Accept | `application/vnd.movie.delete.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful delete of movie |
| HTTP Status code | 401 | If the user is not authenticated |

#### Admin Delete Movie (REST)

To delete movies for the user or for a specific user

| Method     | URL     | 
| :-------------      | :----------:      | 
| DELETE | /api/user/movie | 

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the movie must be deleted |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.movie.delete.v1+json` |
| Accept | `application/vnd.movie.delete.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful delete of movie |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

#### Create User (REST)

| Method     | URL     | 
| :-------------      | :----------:      | 
| POST | /api/user/manage |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.user.add.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful user creation |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "userName" : "abc_admin_created",
    "password" : "password",
    "firstName" : "Test First Name",
    "lastName" : "Test Last Name",
    "role" : "USER"
}
```

#### Read Users (REST)

| Method     | URL     |
| :-------------      | :------------- |
| GET | /api/user/manage |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.user.read.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful read. |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Response**

```
{
    "userDetails": [
        {
            "userName": "user",
            "firstName": "Test First Name",
            "lastName": "Test Last Name",
            "role": "USER",
            "id": "f56bda96-2834-4cd3-95a4-70811a4c94e8"
        },
        {
            "userName": "admin",
            "firstName": "Test First Name",
            "lastName": "Test Last Name",
            "role": "ADMIN",
            "id": "ab402491-09d6-48d2-8c4d-34a7b70f14a4"
        }
    ]
}
```

#### Delete User (REST)

| Method     | URL     | 
| :-------------      | :----------:      | 
| DELETE | /api/user/manage | 

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.user.delete.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID which must be deleted |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful delete. |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

#### Update User (REST)

| Method     | URL     | 
| :-------------      | :----------:      | 
| PUT | /api/user/manage |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.user.update.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful update. |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "userName": "user",
    "password" : "user123",
    "firstName": "Test abc_admin_name",
    "lastName": "Test Last Name",
    "role": "USER"
}
```

### GRPC Endpoints

#### CreateAccount
#### Login

### Some Application HowTo's
[How do I?](./HowTo.MD#How do I?)
