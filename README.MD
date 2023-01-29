# Drone - Backend

This project has been developed with the purpose of taking control of a fleet of drones in charge of transporting Medications. It has been developed in Java (with Maven) using the environment provided by SpringBoot.
It is made up of a main project, which contains the link to three other projects. These three mentioned projects are:
   - **drone-service:** Main service for the control of the drone fleet. SpringBoot, an embedded server and an H2 database with initial data for queries have been developed.
   - **registry-service:** Service in charge of recording drone-service status changes. It is developed with Eureka Server.
   - **admin-service:** Service in charge of monitoring the operation of drone-service. It constitutes a website developed with Spring Admin.

When starting services it is important to note the boot order. These should start in the following order:
1. registry-service
2. admin-service
3. drone-service

These services have been developed with <code>Java 17</code> and <code>Spring Boot 3.0.3</code>.

## Compiling and starting the services

To compile any of these services you can use Maven tasks. These tasks can be executed individually by being located in each one of the subprojects or from the root of the main project (the main project includes all the subprojects since they are incorporated as submodules of it). You can use the following command:
```bash
$ mvn clean package install
```

To run the services you can run the following command:
```bash
$ mvn spring-boot:run
```

On the other hand, you can also run the .jar files generated from each of the projects with the following command:
```bash
$ java -jar target\<filename>
```

## Services Execution
Once the services have been executed, the following URLs will be available:
- http://localhost:8091/swagger-ui/index.html : This URL contains a web page that shows the information of each of the endpoints available in drone-service documented with Swagger. It contains information on the structure of the requests and responses that these endpoints accept.
- http://localhost:8761: Contains the website of the Eureka Server, which allows you to follow the status of the drone-service..
- http://localhost:8086 : Contains the Spring Admin web interface. This service allows you to view the drone-service status.

### Swagger interface with some of the available web services.
![swagger-ui](swagger-ui.png)

### Eureka Server interface.
![eureka-server](eureka-server.png)

### Spring Boot Admin Interface.
![spring-boot-admin](spring-boot-admin.png)