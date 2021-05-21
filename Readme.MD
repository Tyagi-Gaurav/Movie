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

The API has been designed using [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
and [Spring Boot](https://spring.io/projects/spring-boot). [Redis](https://redislabs.com/) is used for storage of
timezones. It can either be used in embedded manner in standalone mode or as a separate entity when running using
docker. The backend storage is accessed via a set of interfaces. It can easily be swapped out for anything else without
affecting rest of the application.

#### Application Security

To access the application, users need a valid `userId` and `password` to login. Once they login, they would be provided
a security token ([JWT](https://jwt.io/))

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
application. It can be found at the root directory of the project by the name of `TimezoneAPI.postman_collection.json`

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
| DATABASE_HOST     | localhost      | The host for database |
| DATABASE_PORT     | 6379      | The port for database |
| DUPLICATE_INTERVAL     | 10s      | The default interval within which if a user sends a duplicate request, that would be rejected. |
| CONNECTION_TIMEOUT     | 10s      | Connection timeout for the database. |
| COMMAND_TIMEOUT     | 500ms      | The default query timeout for all database queries. |
| GracefulShutdownWaitSeconds     | 20 seconds      | The time application waits to terminate after receiving a SIGTERM signal. |

### Endpoints

#### Create User

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

#### Login

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

#### Add Timezone

To add new timezone(s).

| Method     | URL     | 
| :-------------      | :----------:      | 
| POST | /api/user/timezone | 

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| name | String | Timezone Name |
| city | String | Timezone City |
| gmtOffset | integer | Offset to GMT. Valid values must be between `-12` to `+12` (Both Inclusive) |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.add.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful timezone creation |
| HTTP Status code | 401 | If the user is not authenticated |

**Sample Request**

```
{
    "name" : "America/USzfzsfs",
    "city" : "Newyork",
    "gmtOffset" : -12
}
```

#### Admin Add Timezone

This endpoint can be used by admin to create timezones for a specific user.

| Method     | URL     | 
| :-------------      | :----------:      | 
| POST | /api/user/timezone |

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the timezone must be created |

| Attribute | Type | Description |
| :-------------      | :----------:      | :----------:|
| name | String | Timezone Name |
| city | String | Timezone City |
| gmtOffset | integer | Offset to GMT. Valid values must be between `-12` to `+12` (Both Inclusive) |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.add.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 204 | On successful timezone creation |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "name" : "America/USzfzsfs",
    "city" : "Newyork",
    "gmtOffset" : -12
}
```

#### Read Timezone

To read timezones created by the user

| Method     | URL     | 
| :-------------      | :----------:      |
| GET | /api/user/timezone | 

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.read.v1+json` | 
| Accept | `application/vnd.timezone.read.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful read of timezone |
| HTTP Status code | 401 | If the user is not authenticated |

**Sample Response**

```
{
    "timezones": [
        {
            "id": "9caeab84-eeed-4a9d-a964-4d1f7d4d8a7e",
            "name": "America/USzfzsfs",
            "city": "Newyork",
            "gmtOffset": -12
        }
    ]
}
```

#### Admin Read Timezone

To read timezones created by the user

| Method     | URL     | 
| :-------------      | :----------:      |
| GET | /api/user/timezone | 

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the timezone must be created |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.read.v1+json` | 
| Accept | `application/vnd.timezone.read.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful read of timezone |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Response**

```
{
    "timezones": [
        {
            "id": "9caeab84-eeed-4a9d-a964-4d1f7d4d8a7e",
            "name": "America/USzfzsfs",
            "city": "Newyork",
            "gmtOffset": -12
        }
    ]
}
```

#### Update Timezone

To update timezones for the user.

| Method     | URL     | 
| :-------------      | :----------:      |
| PUT | /api/user/timezone |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.update.v1+json` | 
| Accept | `application/vnd.timezone.update.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful update of timezone |
| HTTP Status code | 401 | If the user is not authenticated |

**Sample Request**

```
{
    "id": "c48f3735-021b-4c20-9b7f-30cf4202fb7d",
    "city": "Boston"
}
```

Any of the timezone fields could be changed, except for the `id` field as it uniquely identifies the timezone.

#### Admin Update Timezone

To update timezones for the user or for a specific user.

| Method     | URL     | 
| :-------------      | :----------:      |
| PUT | /api/user/timezone |

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the timezone must be updated |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.update.v1+json` | 
| Accept | `application/vnd.timezone.update.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful update of timezone |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

**Sample Request**

```
{
    "id": "c48f3735-021b-4c20-9b7f-30cf4202fb7d",
    "city": "Boston"
}
```

Any of the timezone fields could be changed, except for the `id` field as it uniquely identifies the timezone.

#### Delete Timezone

To delete timezones for the user

| Method     | URL     | 
| :-------------      | :----------:      | 
| DELETE | /api/user/timezone | 

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.delete.v1+json` | 
| Accept | `application/vnd.timezone.delete.v1+json` | 
| Authorization | `Bearer <Token From login response>` | 

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful delete of timezone |
| HTTP Status code | 401 | If the user is not authenticated |

#### Admin Delete Timezone

To delete timezones for the user or for a specific user

| Method     | URL     | 
| :-------------      | :----------:      | 
| DELETE | /api/user/timezone | 

| Query Parameter | Value Type | Description |
| :-------------      | :----------:      | :----------:      |
| userId | UUID | User ID for which the timezone must be deleted |

| Headers | Value |
| :-------------      | :----------:      |
| Content-Type | `application/vnd.timezone.delete.v1+json` |
| Accept | `application/vnd.timezone.delete.v1+json` |
| Authorization | `Bearer <Token From login response>` |

| Response Type | Value/Value Type | Description |
| :------------- | :-------------      | :-------------      |
| HTTP Status code | 200 | On successful delete of timezone |
| HTTP Status code | 401 | If the user is not authenticated |
| HTTP Status code | 403 | If the user is not authorised |

#### Create User

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

#### Read Users

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

#### Delete User

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

#### Update User

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