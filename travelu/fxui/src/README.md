# Restserver

The restserver module is responsible for handling transfer of data between backend and frontend using a RESTful web service.

### Running

From travelu folder
- `mvn install`
- `mvn -pl restserver spring-boot:run`

The application is hosted on **localhost** port **8080**.  
Manual testing can be done by opening [localhost:8080](http://localhost:8080/) in browser and appending api address.  
Ex: [http://localhost:8080/api/v1/entries/destinationList](http://localhost:8080/api/v1/entries/destinationList)

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

## Service

The Service is responsible for communicating with the module handling local persistence.  
The class mainly does two things: load and save. It also give the controller access to (not a copy) of the interal object to able to write these changes to file.

<br>

# REST API methods

## GET

### getDestinationListJSON()

- API address: `/api/v1/entries/destinationlist`

- Inputs: None

- Returns: `String`: The JSON file [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json) as a string.

```json
{
  "destinations": [
    {
      "name": "Sweden",
      "dateInterval": {
        "arrivalDate": "02/11/2022",
        "departureDate": "26/11/2022"
      },
      "rating": 2,
      "activities": [
        "Go hiking",
        "Go skiing"
      ],
      "comment": "Visiting Sweden was a lot of fun. I love traveling with friends!"
    },
    {
      "name": "USA",
      "dateInterval": {
        "arrivalDate": null,
        "departureDate": null
      },
      "rating": 4,
      "activities": [
        "Visit Walmart",
        "Eat at Taco Bell"
      ],
      "comment": "A family trip to the USA never fails!"
    }
  ]
}
```

### getDestinationJSON(String destinationName)

- API address: `/api/v1/entries/{destinationName}`

- Inputs: `String destinationName`: Name of destination to get

- Returns: `String`: The destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json) with the inputted name as a string.

```json
{
  "name": "Sweden",
  "dateInterval": {
    "arrivalDate": "02/11/2022",
    "departureDate": "26/11/2022"
  },
  "rating": 2,
  "activities": [
    "Go hiking",
    "Go skiing"
  ],
  "comment": "Visiting Sweden was a lot of fun. I love traveling with friends!"
}
```

### getDestinationJSON()

- API address: `/api/v1/entries/currentDestination`

- Inputs: None

- Returns: `String`: The JSON file [CurrentDestination.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/CurrentDestination.json) as a string.

```json
{
  "name": "Sweden",
  "dateInterval": {
    "arrivalDate": "02/11/2022",
    "departureDate": "26/11/2022"
  },
  "rating": 2,
  "activities": [
    "Go hiking",
    "Go skiing"
  ],
  "comment": "Visiting Sweden was a lot of fun. I love traveling with friends!"
}
```


## POST

### storeCurrentDestinationJSON(String destinationNameJSON)

- API address: `/api/v1/entries/storeCurrent`

- Inputs: `String destinationNameJSON`: The name of the destination that should be stored in [CurrentDestination.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/CurrentDestination.json).


### addDestinationJSON(String destinationJSON)

- API address: `/api/v1/entries/add`

- Inputs: `String destinationJSON`: The destination information to add to [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json) as a JSON converted to string.


### removeDestinationJSON(String destinationName)

- API address: `/api/v1/entries/remove`

- Inputs: `String destinationNameJSON`: The name of the destination that should be removed from [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### addActivityJSON(String activity)

- API address: `/api/v1/entries/addActivity`

- Inputs: `String activity`: The activity to add to the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### removeActivityJSON(String activity)

- API address: `/api/v1/entries/removeActivity`

- Inputs: `String activity`: The activity to remove from the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### setRatingJSON(String rating)

- API address: `/api/v1/entries/setRating`

- Inputs: `String rating`: The rating to set to the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### setArrivalDateJSON(String arrivalDate)

- API address: `/api/v1/entries/setArrivalDate`

- Inputs: `String arrivalDate`: The arrival date to set to the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### setDepartureDateJSON(String departureDate)

- API address: `/api/v1/entries/setDepartureDate`

- Inputs: `String departureDate`: The departure date to set to the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### updateCommentJSON(String commentJSON)

- API address: `/api/v1/entries/updateComment`

- Inputs: `String commentJSON`: The comment to set to the currently selected destination in [destinationList.json](/travelu/localpersistence/src/main/resources/travelu/localpersistence/data/DestinationList.json).


### Sources
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Spring Boot Tutorial](https://www.baeldung.com/spring-boot-start)
- [Quick Guide Spring Controllers](https://www.baeldung.com/spring-controllers)
- [Dispatch Servlet](https://www.geeksforgeeks.org/what-is-dispatcher-servlet-in-spring/)
- [SpringBootApplication Annotation](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html) 
