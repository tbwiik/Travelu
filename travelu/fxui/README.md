# User Interface

The User Interface (UI) module is resposible for handeling the user interface of the application. It is the view of the application. The UI module is divided into two parts, the FXML resources and the JavaFX controllers. The FXML resources are the files that define the layout of the UI. The JavaFX controllers are the files that define the behaviour of the UI. Together they make up the UI which allows the user to interact with the application.

The UI module is connected to the domain logic module through a REST API. The best way of handling the connection between the UI and the domain logic (core) is to make all the information go through the API. However, our controllers currently access our domain logic directly. This is not optimal, since seperating the controller and backend allows the frontend to be changed without having to change the backend. This is a good thing, since the frontend is the part of the application that the user interacts with. If the user doesn't like the frontend, they can change it without having to change the backend. This is not the case if the frontend and backend are connected. If the frontend and backend are connected, the user is forced to change the backend if they want to change the frontend. Therefore, this is something that should be fixed if the app goes further into development.

## App

The [App](/travelu/fxui/src/main/java/travelu/fxui/App.java) class is the class that starts the application. It loads the destination list view and shows it to the user.

## JavaFX controllers

The app has to seperate controllers for the different views. They control each of their respective views.

### DestinationListController

The [DestinationListController](/travelu/fxui/src/main/java/travelu/fxui/DestinationListController.java) is responsible for the destination list view. It is responsible for loading the destinations from the rest-api and displaying them in a list-view. It also handles the buttons for adding and removing destinations, sorting them by name and rating, and double-clicking a destination to open the destination view.

### DestinationController

The [DestinationController](/travelu/fxui/src/main/java/travelu/fxui/DestinationController.java) is responsible for the destination view. It is responsible for loading the current destination along with its information from the rest-api and displaying it. It also handles setting arrival and departure dates for the destination, adding and removing activities, setting rating and updating the comment.

To see what each individual method does, see the javadoc for the classes.


## FXML resources

The app also has two seperate FXML files for the different views. They define the layout of the UI, what the user sees. To make it possible for the controller to retrieve the FXML elements, each element is given an id. The controllers can then get the element by using the id.

### Destination List View

The [destinationList.fxml](/travelu/fxui/src/main/resources/travelu/fxui/destinationList.fxml) is the main view that loads when the app starts. It displays a list of destinations that the user has added. It also has buttons for adding and removing destinations as well as sorting them by name and rating.

### Destination View

The [destination.fxml](/travelu/fxui/src/main/resources/travelu/fxui/destination.fxml) is the secondary view of the app, and displays information about the current destination. It displays the name, arrival and departure date, rating, comment and activities. It also has date pickers, list-views, buttons and text fields for setting arrival and departure dates, adding and removing activities, setting rating and updating the comment.