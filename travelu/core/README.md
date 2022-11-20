# Core

The core module is the domain logic of the application. It is a part of the backend of the application and is responsible for processing data and handling the domain logic. Since the app handles a lot of inputs from the user, the app also needs a lot of input validation. This is mostly handled by core. To display these errors to the user however, the methods throw exceptions which are caught by the UI module and displayed to the user.

## Destination List

The [DestinationList](/travelu/core/src/main/java/travelu/core/DestinationList.java) class is a class that represents a list of destinations. It contains a list of destinations and methods for adding, removing and sorting the destinations in this list.

## Destination

The [Destination](/travelu/core/src/main/java/travelu/core/Destination.java) class is the class that represents a destination. It is a data class that contains information about the destination such as name, arrival and departure date, rating, comment and activities as well as methdos for setting and getting this information.

## Date Interval

The [DateInterval](/travelu/core/src/main/java/travelu/core/DateInterval.java) class is a class that represents a date interval. It contains an arrival date and a departure date. It also contains methods for checking if a given date is valid and that arrival date is set before departure date.