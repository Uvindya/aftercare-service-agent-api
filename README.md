# After Care Agent API

GitHub
------------

```sh
https://github.com/Uvindya/aftercare-service-agent-api
```

Tech Stack
------------

- Java 11
- Maven 3
- Spring boot 2.1.4
- JPA + Hibernate
- Dev Tools/Actuator
- Swagger 2 API documentation

Starting Application
------------

You need to update your profile based configurations (DB configurations, etc...) before you starting the application

-  to run locally :

```sh
$ mvn spring-boot:run
```

Access Swagger Documentation
------------

```sh
http://localhost:8080/swagger-ui.html
```

Checking Application Info
------------

```sh
http://localhost:8080/actuator/info
```

Checking Application health
------------

```sh
http://localhost:8080/actuator/health
```