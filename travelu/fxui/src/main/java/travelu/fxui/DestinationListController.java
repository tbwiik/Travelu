package travelu.fxui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import travelu.client.Client;
import travelu.client.ServerException;
import travelu.core.Destination;
import travelu.core.DestinationList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class DestinationListController {

    /**
     * Initialize client for server communication
     */
    private final Client client = new Client("http://localhost", 8080);

    /**
     * ListView containing names of all destinations in DestinationList
     */
    @FXML
    private ListView<String> listView;

    /**
     * TextArea for adding destination by typing in name
     */
    @FXML
    private TextArea destinationText;

    /**
     * Label for giving feedback related to destination list
     */
    @FXML
    private Label feedbackLabel;

    /**
     * DestinationList object, contains all destinations
     */
    private DestinationList destinationList;

    /**
     * Currently selected destination, used for changing views
     */
    private String currentDestination;

    /**
     * Initialize start-page.
     */
    @FXML
    private void initialize() {

        try {
            // Get destination list from server
            this.destinationList = client.getDestinationList();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

        setUpListView();

    }

    /**
     * Make the list view be a list of all destination names in the desitnation
     * list.
     * 
     * Each name with a number of stars at the end equal to the rating.
     */
    private void setUpListView() {

        // Set font size in list view to 20px
        listView.setStyle("-fx-font-size:20;");

        // Clear list view
        listView.getItems().clear();

        // Create list of all destinations with star-rating
        List<String> destinationNameAndRating = new ArrayList<>();
        for (String destinationName : destinationList.getDestinationNames()) {
            try {
                // Get rating of destination
                int destinationRating = destinationList.getDestinationCopyByName(destinationName).getRating();
                // Add destination with name and number stars equal to rating
                destinationNameAndRating.add(destinationName + "★".repeat(destinationRating));
            } catch (NoSuchElementException nsee) {
                feedbackLabel.setText("No such element: " + nsee.getMessage());
            }

        }

        // Add all destinations and rating to list-view
        listView.getItems().addAll(destinationNameAndRating);

        // Make click select currentDestination
        // Make double-click on list-view item take you to page with currentDestination
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getTarget().toString().contains("'")) {
                    // If you click on the box around the text the format is
                    // objectinformation'DestinationName'
                    // We then need to get the element after the first '
                    currentDestination = click.getTarget().toString()
                            .split("'")[1];
                } else if (click.getTarget().toString().contains("\"")) {
                    // If you click directly on the text the format is
                    // Text[text="DestinationName" objectinformation="..."]]
                    // We then need to get the element after the first "
                    currentDestination = click.getTarget().toString()
                            .split("\"")[1];
                }

                // If the name of the current destination is "null", we want it to be null
                // instead
                if ("null".equals(currentDestination)) {
                    currentDestination = null;
                }

                // Switch to desitnation page on double-click
                if (click.getClickCount() == 2) {
                    // If currentDestination is null, the user has not clicked a destination
                    if (currentDestination != null) {
                        // Remove the stars from the selected destination
                        String currentDestinationName = currentDestination.replace("★", "");
                        try {
                            // Load the destination chosen
                            switchToDestination(currentDestinationName);
                        } catch (IOException e) {
                            // Give feedback to user that the destination could not be found
                            feedbackLabel.setText("Could not find " + currentDestinationName);
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * Switch to Destination-View
     * 
     * @param destinationName
     * @throws IOException if error initializing new page
     */
    private void switchToDestination(String destinationName) throws IOException {

        try {
            // Store the currently selected destination's name to the server
            client.storeCurrentDestinationName(destinationName);
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

        App.setRoot("destination");

    }

    /**
     * Add destination to list
     */
    @FXML
    private void handleAddDestination() {
        String newDestinationName = destinationText.getText().trim();
        try {
            if (newDestinationName.isBlank()) {
                // If user didn't input any text
                // Remove any feedback given and do nothing
                feedbackLabel.setText("");
            } else if (newDestinationName.equals("null")) {
                // because of how getting items from list-views work, we do not allow the user
                // to add a destination with the name "null"
                feedbackLabel.setText("The name null is invalid for a destination");
            } else if (destinationList.containsDestination(newDestinationName)) {
                // If the input text matches any of the already existing registrations
                // Give feedback
                feedbackLabel.setText("You have already registered this destination");
            } else if (!newDestinationName.matches("[A-Za-z\\s\\-]+")) {
                // If the input text only contains letters, spaces and dashes
                feedbackLabel.setText("Destination name must contain only letters, spaces and dashes");
            } else {
                // If the input is valid
                // Create new destination with input as name
                Destination newDestination = new Destination(newDestinationName.strip(), null, 0,
                        new ArrayList<String>(), null);

                // Add destination to list-view and destinations
                listView.getItems().add(newDestination.getName());
                destinationList.addDestination(newDestination);

                // Remove any feedback given
                feedbackLabel.setText("");

                // Remove text in inputField
                destinationText.clear();

                // Add destination to server
                client.addDestination(newDestination);
            }
        } catch (IllegalArgumentException iae) {
            feedbackLabel.setText("");
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Removes destination from list
     */
    @FXML
    private void handleRemoveDestination() {
        if (currentDestination == null) {
            // If there is no selected destination
            // Give user feedback
            feedbackLabel.setText("Please select a destination you would like to remove");
        } else {
            // If there is a selected destination
            // Remove the selected destination from destinations and list-view
            // Remove the star-rating from the selected destination
            String currentDestinationName = currentDestination.replace("★", "");

            try {
                client.removeDestination(currentDestinationName);

                // Remove the destination from destinationList and list-view
                destinationList.removeDestination(currentDestinationName);
                listView.getItems().remove(currentDestination);
                currentDestination = null;

                // Clear feedback if everything went well
                feedbackLabel.setText("");

            } catch (NoSuchElementException nsee) {
                feedbackLabel.setText("Please select a destination you would like to remove");
            } catch (ServerException se) {
                errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
            } catch (ExecutionException | URISyntaxException | InterruptedException e) {
                errorPopup("Error", "Error: \n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Sort the list of destinations alphabetically from A to Z
     */
    @FXML
    private void handleSortByName() {
        destinationList.sortByName();
        setUpListView();
    }

    /**
     * Sort the list of destinations by rating from highest to lowest
     */
    @FXML
    private void handleSortByRating() {
        destinationList.sortByRating();
        setUpListView();
    }

    /*
     * Creates a popup with the given title and message that will be shown until
     * closed
     */
    private void errorPopup(String type, String message) {
        Alert invalidInput = new Alert(AlertType.WARNING);
        invalidInput.setTitle(type);
        invalidInput.setHeaderText(message);
        invalidInput.showAndWait();
    }

}
