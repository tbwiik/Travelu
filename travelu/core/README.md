# Core

The core module contains the domain logic for the application. It's a part of the backend of the application, and is responsible for processing data and handling logic. There application are interactive and have many fields for input. This need validation and core handles the part of this that are independent of frontend. To be able to display these errors for the user, exceptions are thrown and caught in UI where they get displayed.

## Destination List

The [DestinationList](/travelu/core/src/main/java/travelu/core/DestinationList.java) class is a class that represents a list of destinations. It contains a list of destinations and methods for adding, removing and sorting the destinations in this list.

## Destination

The [Destination](/travelu/core/src/main/java/travelu/core/Destination.java) class is the class that represents a destination. It is a data class that contains information about the destination such as name, arrival and departure date, rating, comment and activities as well as methdos for setting and getting this information.

## Date Interval

The [DateInterval](/travelu/core/src/main/java/travelu/core/DateInterval.java) class is a class that represents a date interval. It contains an arrival date and a departure date. It also contains methods for checking if a given date is valid and that arrival date is set before departure date.