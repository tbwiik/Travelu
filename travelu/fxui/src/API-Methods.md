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
"Sweden"
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

