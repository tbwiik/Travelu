# Restserver

The restserver module is responsible for handling transfer of data between backend and frontend using a RESTful web service.

### Running

From travelu folder
- `mvn install`
- `mvn -pl restserver spring-boot:run`

The application is hosted on **localhost** port **8080**.  
Manual testing of getters can be done by opening [localhost:8080](http://localhost:8080/) in browser and appending api address.  
Ex: [http://localhost:8080/api/v1/entries/destinationlist](http://localhost:8080/api/v1/entries/destinationlist)

## Application

The application initializes the server.  
Even though the application class is very small codewise there is a lot going on under the hood due to the `@SpringBootApplication` annotation.  
Without going into too much detail this class sets up the server using a DispatchServlet (aka Front Controller) as the entrypoint for the client on the other side further delegating tasks to the controllers.


## Controller

The controller is where requests are handled.   
While we have chosen to have everything going through one, it is quite possible to have several. 
The controller is responsible for loading from, or saving to, local storage depending on the request, the most standard being GET and POST respectively.  
Here the api- and request-addresses are defined.  
Persistence is handled by the Service.

See [API-Methods](/travelu/restserver/api-methods.md)

## Service

The Service is responsible for communicating with the module handling local persistence.  
The class mainly does two things: load and save. It also give the controller access to (not a copy) of the interal object to able to write these changes to file.

## Sources
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Spring Boot Tutorial](https://www.baeldung.com/spring-boot-start)
- [Quick Guide Spring Controllers](https://www.baeldung.com/spring-controllers)
- [Dispatch Servlet](https://www.geeksforgeeks.org/what-is-dispatcher-servlet-in-spring/)
- [SpringBootApplication Annotation](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html) 
